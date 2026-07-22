package com.danymateo.soap;

import jakarta.xml.ws.Endpoint;

public class SoapApplication {

    public static void main(String[] args) {

        String url = "http://localhost:8081/suma";

        Endpoint.publish(url, new SumaService());

        System.out.println("Servidor SOAP iniciado");
        System.out.println("WSDL disponible en:");
        System.out.println(url + "?WSDL");
    }
}