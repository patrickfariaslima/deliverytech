package com.deliverytech.delivery_api.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Health", description = "Endpoints de saúde e informações do serviço")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Verificar saúde da aplicação")
    @ApiResponse(responseCode = "200", description = "Serviço saudável")
    public Map<String, String> health() {
        return Map.of(
            "status", "UP",
            "time", LocalDateTime.now().toString(),
            "service", "Delivery API",
            "javaVersion", System.getProperty("java.version")
        );
    }

    @GetMapping("/info")
    @Operation(summary = "Informações da aplicação")
    @ApiResponse(responseCode = "200", description = "Informações retornadas")
    public AppInfo info() {
        return new AppInfo(
            "Delivery Tech API",
            "1.0.0",
            "Patrick Farias Lima",
            "JDK 21",
            "Spring Boot 3.5.9"
        );
    }

    public record AppInfo(
        String application,
        String version,
        String developer,
        String kavaVersion,
        String framework
    ) {}
    
}
