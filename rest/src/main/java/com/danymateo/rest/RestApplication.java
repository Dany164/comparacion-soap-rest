package com.danymateo.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class RestApplication {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );

        server.createContext("/suma", RestApplication::manejarSuma);

        server.start();

        System.out.println("Servidor REST iniciado");
        System.out.println("Disponible en: http://localhost:8080");
        System.out.println("Ejemplo: http://localhost:8080/suma?a=5&b=3");
    }

    private static void manejarSuma(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {

            String respuesta = """
                    {
                        "error": "Método HTTP no permitido"
                    }
                    """;

            enviarRespuesta(exchange, respuesta, 405);
            return;
        }

        try {

            String query = exchange.getRequestURI().getQuery();

            if (query == null) {
                enviarRespuesta(exchange, """
                        {
                            "error": "Debe proporcionar los parámetros a y b"
                        }
                        """, 400);
                return;
            }

            String[] parametros = query.split("&");

            double a = 0;
            double b = 0;

            boolean existeA = false;
            boolean existeB = false;

            for (String parametro : parametros) {

                String[] partes = parametro.split("=");

                if (partes.length != 2) {
                    continue;
                }

                String nombre = partes[0];
                String valor = partes[1];

                if (nombre.equals("a")) {
                    a = Double.parseDouble(valor);
                    existeA = true;
                }

                if (nombre.equals("b")) {
                    b = Double.parseDouble(valor);
                    existeB = true;
                }
            }

            if (!existeA || !existeB) {

                enviarRespuesta(exchange, """
                        {
                            "error": "Debe proporcionar los parámetros a y b"
                        }
                        """, 400);

                return;
            }

            double resultado = a + b;

            String respuesta = """
                    {
                        "operacion": "suma",
                        "primerNumero": %s,
                        "segundoNumero": %s,
                        "resultado": %s
                    }
                    """.formatted(a, b, resultado);

            enviarRespuesta(exchange, respuesta, 200);

        } catch (NumberFormatException e) {

            String respuesta = """
                    {
                        "error": "Los valores deben ser números válidos"
                    }
                    """;

            enviarRespuesta(exchange, respuesta, 400);
        }
    }

    private static void enviarRespuesta(
            HttpExchange exchange,
            String respuesta,
            int codigo
    ) throws IOException {

        exchange.getResponseHeaders()
                .set("Content-Type", "application/json; charset=UTF-8");

        byte[] bytes = respuesta.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(codigo, bytes.length);

        exchange.getResponseBody().write(bytes);

        exchange.getResponseBody().close();
    }
}