package com.servicio.spring.appnomina.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicio.spring.appnomina.model.FormData;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class JsonGeneratorController {

    @GetMapping("/")
    public String showForm(FormData formData) {
        return "form";
    }

    @PostMapping("/generate-json")
    public void generateJson(@ModelAttribute FormData formData, HttpServletResponse response) throws IOException {
        // Convertir el objeto a JSON
        String json = convertToJson(formData);

        // Configurar la respuesta para descarga
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=\"datos.json\"");
        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
    }

    private String convertToJson(FormData formData) {
    return String.format(
        "{\"nombre\":\"%s\",\"cuentaDestino\":\"%s\",\"bancoDestino\":\"%s\",\"monto\":%.2f,\"concepto\":\"%s\"}",
        formData.getNombre(), 
        formData.getCuentaDestino(), 
        formData.getBancoDestino(), 
        formData.getMonto(), 
        formData.getConcepto()
    );
}

    @PostMapping("/upload-json")
    public String uploadJson(@RequestParam("file") MultipartFile file, Model model) {
    if (file.isEmpty()) {
        model.addAttribute("mensaje", "Por favor selecciona un archivo.");
        return "form";
    }

    try {
        ObjectMapper mapper = new ObjectMapper();
        FormData formData = mapper.readValue(file.getInputStream(), FormData.class);
        
        // Agregar los datos al modelo para mostrarlos en la vista
        model.addAttribute("formData", formData);
        model.addAttribute("mensaje", "JSON subido correctamente");

    } catch (Exception e) {
        model.addAttribute("mensaje", "Error al leer el JSON: " + e.getMessage());
    }

    return "form";
}
    
}

