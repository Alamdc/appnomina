package com.servicio.spring.appnomina.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicio.spring.appnomina.model.FormData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

@Controller
public class JsonGeneratorController {

    @GetMapping("/")
    public String showForm(Model model) {
        return "form";
    }

    @PostMapping("/upload-json")
    public String uploadJson(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("mensaje", "Por favor selecciona un archivo.");
            return "form";
        }

        try {
            ObjectMapper mapper = new ObjectMapper();

            // Leer JSON como lista de empleados
            List<FormData> empleados = mapper.readValue(file.getInputStream(), new TypeReference<List<FormData>>() {});
            
            model.addAttribute("empleados", empleados);
            return "ver-datos";

        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al leer el JSON: " + e.getMessage());
            return "form";
        }
    }

    @PostMapping("/enviar-nomina")
    public String enviarNomina(Model model) throws InterruptedException {
        // Simular tiempo de espera (como pantalla de carga)
        Thread.sleep(2000); // 2 segundos

        model.addAttribute("mensaje", "Datos enviados exitosamente");
        return "resultado-envio";
    }
}
