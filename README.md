# Comparación entre API SOAP y API REST en Java

## Descripción del proyecto

Este proyecto implementa la misma operación utilizando dos estilos diferentes de servicios web: una API SOAP y una API REST.

La operación implementada consiste en sumar dos números. El objetivo es comparar ambos enfoques y comprender sus diferencias en cuanto a estructura, comunicación, formato de datos y forma de implementación.

---

## Requisitos del proyecto

Para ejecutar el proyecto se necesita:

- Java 17.
- Maven.
- IntelliJ IDEA u otro IDE compatible con proyectos Maven.
- Postman o cualquier cliente HTTP/SOAP para realizar las pruebas.
- Conexión a Internet para descargar las dependencias de Maven la primera vez.

### Versión de Java

```text
Java 17.0.19
```

### Gestor de dependencias

El proyecto utiliza:

```text
Apache Maven
```

Maven se utiliza para administrar las dependencias, compilar el proyecto y organizar los módulos SOAP y REST.

---

## Estructura del proyecto

```text
comparacion-soap-rest/
│
├── README.md
├── .gitignore
├── pom.xml
│
├── rest/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│
├── soap/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│
└── wsdl/
    └── servicio.wsdl
```

---

## API REST

### Descripción

La API REST implementa una operación para sumar dos números.
La solicitud se realiza utilizando el método HTTP `GET`.

### Endpoint

```text
http://localhost:8080/suma?a=5&b=3
```

### Ejemplo de respuesta

```json
{
    "operacion": "suma",
    "primerNumero": 5.0,
    "segundoNumero": 3.0,
    "resultado": 8.0
}
```

### Códigos HTTP utilizados

- `200 OK`: operación realizada correctamente.
- `400 Bad Request`: parámetros faltantes o valores inválidos.
- `405 Method Not Allowed`: método HTTP no permitido.

### Cómo ejecutar el servicio REST

1. Abrir el proyecto en IntelliJ IDEA.
2. Ejecutar la clase:

   ```text
   com.danymateo.rest.RestApplication
   ```

3. El servidor estará disponible en:

   ```text
   http://localhost:8080
   ```

4. Para realizar una suma, utilizar:

   ```text
   http://localhost:8080/suma?a=5&b=3
   ```

### Prueba del servicio REST

La operación fue probada mediante una solicitud HTTP:

```http
GET http://localhost:8080/suma?a=5&b=3
```

Respuesta obtenida:

```json
{
    "operacion": "suma",
    "primerNumero": 5.0,
    "segundoNumero": 3.0,
    "resultado": 8.0
}
```

## Captura de la prueba REST

![Prueba REST](capturas/prueba-rest.png)

---

## API SOAP

### Descripción

La API SOAP implementa la misma operación de suma utilizando un servicio web SOAP.
La operación disponible es:

```java
sumar(double a, double b);
```

El servicio utiliza XML para la comunicación y cuenta con un contrato WSDL.

### Endpoint SOAP

```text
http://localhost:8081/suma
```

### WSDL

El contrato WSDL puede consultarse en:

```text
http://localhost:8081/suma?WSDL
```

También se incluye una copia del contrato en:

```text
wsdl/servicio.wsdl
```

### Cómo ejecutar el servicio SOAP

1. Abrir el proyecto en IntelliJ IDEA.
2. Ejecutar la clase:

   ```text
   com.danymateo.soap.SoapApplication
   ```

3. El servicio estará disponible en:

   ```text
   http://localhost:8081/suma
   ```

4. El WSDL estará disponible en:

   ```text
   http://localhost:8081/suma?WSDL
   ```

### Cómo consumir el servicio SOAP

Se puede utilizar Postman o SoapUI.

**Método**

```text
POST
```

**URL**

```text
http://localhost:8081/suma
```

**Header**

```text
Content-Type: text/xml; charset=utf-8
```

**Body**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<soap:Envelope
        xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
        xmlns:ser="http://soap.danymateo.com/">

    <soap:Header/>

    <soap:Body>
        <ser:sumar>
            <arg0>5</arg0>
            <arg1>3</arg1>
        </ser:sumar>
    </soap:Body>

</soap:Envelope>
```

**Respuesta obtenida**

```xml
<?xml version='1.0' encoding='UTF-8'?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    <S:Body>
        <ns2:sumarResponse xmlns:ns2="http://soap.danymateo.com/">
            <return>8.0</return>
        </ns2:sumarResponse>
    </S:Body>
</S:Envelope>
```
## Prueba de la API SOAP
![Prueba SOAP](capturas/prueba-soap.png)

## WSDL generado
![WSDL generado](capturas/wsdl.png)

## Comparación entre SOAP y REST

Durante el desarrollo encontramos que REST fue más sencillo y rápido de implementar porque utiliza directamente HTTP y permite trabajar con respuestas en formato JSON. SOAP requiere una estructura más formal basada en XML y un contrato WSDL que define las operaciones disponibles. Consideramos que SOAP puede utilizarse en sistemas empresariales que necesitan contratos estrictos y una estructura formal de comunicación. REST resulta más conveniente para aplicaciones web, aplicaciones móviles y sistemas que necesitan una comunicación sencilla y flexible. La experiencia nos permitió observar que REST es más simple para comenzar, mientras que SOAP proporciona una estructura más rígida y formal.