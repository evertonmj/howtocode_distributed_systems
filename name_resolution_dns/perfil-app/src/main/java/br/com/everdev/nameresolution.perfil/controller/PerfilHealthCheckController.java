package br.com.everdev.nameresolution.perfil.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
=======
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
>>>>>>> ricardo-branch
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PerfilHealthCheckController {
<<<<<<< HEAD
=======

>>>>>>> ricardo-branch
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/health")
    public String healthy() {
        return "Estpu vivo e bem! Sou a app " + appName + " - " + LocalDateTime.now();
    }

    @PostMapping("/emails/perfil")
    public String getProfile(@RequestBody Map<String, String> requestBody) {
        try {
            // Extrair o email do corpo do JSON
            String email = requestBody.get("email");

            // Construir a URL de destino
            String destinationUrl = "http://192.168.0.102:8182/emails/perfil";

            // Criar o corpo da requisição em formato JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(Map.of("email", email));

            // Criar a solicitação HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(destinationUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Enviar a solicitação e obter a resposta
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Retornar a resposta do servidor
            return response.body();
        } catch (Exception e) {
            // Em caso de exceção, retornar uma mensagem de erro
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }
<<<<<<< HEAD
=======

    @GetMapping("/obterArquivo/{filename}")
    public String obterArquivo(@PathVariable String filename) {
        try {
            // Construir a URL com o IP e a porta do destino
            String url = "http://localhost:8089/dfs-a-upload/obterArquivo/"+filename;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

>>>>>>> ricardo-branch
}
