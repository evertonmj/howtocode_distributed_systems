package br.com.everdev.nameresolution.dnsserver.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DNSServerHealthCheckController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/health")
    public String healthy() {
        return "Sou o DNS Server e estou online: " + LocalDateTime.now();
    }

    @GetMapping("/ppp")
    public String ppp() {
        return "Sou o DNS Server e estou online: " + LocalDateTime.now();
    }

    @GetMapping("/return")
    public String returnActiveAppIPs() throws URISyntaxException {

        try {
            // Monta a requisição para obter a lista de aplicativos no Eureka
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8761/eureka/apps"))
                    .GET()
                    .build();

            // Envia a requisição e obtém a resposta
            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica se a resposta foi bem-sucedida
            if (response.statusCode() == 200) {
                String responseBody = response.body();

                // Extrai os endereços IP das aplicações ativas do corpo da resposta
                List<String> activeAppIPs = new ArrayList<>();
                int startIndex = responseBody.indexOf("<ipAddr>");
                while (startIndex != -1) {
                    int endIndex = responseBody.indexOf("</ipAddr>", startIndex);
                    String ipAddr = responseBody.substring(startIndex + "<ipAddr>".length(), endIndex);

                    // Adiciona o endereço IP à lista se a aplicação estiver ativa
                    int statusIndex = responseBody.lastIndexOf("<status>", endIndex);
                    String status = responseBody.substring(statusIndex + "<status>".length(), responseBody.indexOf("</status>", statusIndex));
                    if (status.equals("UP")) {
                        activeAppIPs.add(ipAddr);
                    }

                    startIndex = responseBody.indexOf("<ipAddr>", endIndex);
                }

                if (activeAppIPs.isEmpty()) {
                    return "Nenhuma aplicação ativa encontrada.";
                }

                // Constrói a string com os endereços IP ativos
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < activeAppIPs.size(); i++) {
                    stringBuilder.append(activeAppIPs.get(i));
                    if (i < activeAppIPs.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                return stringBuilder.toString();

            } else {
                return "Falha ao obter a lista de aplicativos ativos. Código de resposta: " + response.statusCode();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
