package com.servicio.spring.appnomina.model;

import lombok.Data;

@Data
public class FormData {
    private String nombre;
    private String cuentaDestino;
    private String bancoDestino;
    private double monto;
    private String concepto;
}
