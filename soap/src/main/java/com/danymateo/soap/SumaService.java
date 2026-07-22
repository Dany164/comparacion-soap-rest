package com.danymateo.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public class SumaService {

    @WebMethod
    public double sumar(double a, double b) {
        return a + b;
    }
}