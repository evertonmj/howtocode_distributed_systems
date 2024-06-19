package br.com.everdev.nameresolution.ispserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

=======
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
>>>>>>> ricardo-branch
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ISPServerHealthCheckController {


    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/health")
    public String healthy() {
        return "Sou o ISP Server e estou online!" + LocalDateTime.now();
    }


    @PostMapping("/validacao")
    public String sendValidation(@RequestBody Map<String, String> payload) {
        try {
            // Extrair os dados do payload
            String email = payload.get("email");
            String nome = payload.get("nome");
            String perfil = payload.get("perfil");

            // Construir o objeto representando os dados
            Map<String, String> requestData = new HashMap<>();
            requestData.put("email", email);
            requestData.put("nome", nome);
            requestData.put("perfil", perfil);

            // Construir a URL com a rota para obter o IP do servidor DNS
            String dnsUrl = "http://localhost:8081/return";

            // Criar a solicitação HTTP GET para obter o IP
            HttpRequest dnsRequest = HttpRequest.newBuilder()
                    .uri(new URI(dnsUrl))
                    .GET()
                    .build();

            // Enviar a solicitação ao servidor DNS e obter a resposta
            HttpResponse<String> dnsResponse = HttpClient.newHttpClient().send(dnsRequest, HttpResponse.BodyHandlers.ofString());

            // Extrair o IP do corpo da resposta do servidor DNS
            String ip = dnsResponse.body();


             /* DINAMICAMENTE  FUNCIONANDO */

            // Construir a URL com o IP obtido
            String url = "http://" + ip + ":8182/validacao";

            // Criar o corpo da requisição em formato JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(requestData);

            // Criar a solicitação HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Enviar a solicitação e obter a resposta
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Obter o status code da resposta
            int statusCode = response.statusCode();

            // Retornar o status code como uma string
            return "Status code: " + statusCode;
        } catch (Exception e) {
            // Em caso de exceção, retornar uma mensagem de erro
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }


    /*Pegando resposta do DNS IP*/
    @GetMapping("/return")
    public String returnIP() {
        try {
            // URL do servidor DNS simulado e do método desejado
            String dnsServerUrl = "http://localhost:8182/emails" ;

            // Criação da solicitação HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(dnsServerUrl))
                    .GET()
                    .build();

            // Cliente HTTP para enviar a solicitação
            HttpClient client = HttpClient.newHttpClient();

            // Envio da solicitação e obtenção da resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Retorna a resposta JSON do método dentro do servidor DNS
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Em caso de exceção, retorna uma mensagem de erro
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }
<<<<<<< HEAD
/********************************************************************************************/
@PostMapping("/profile")
public String sendProfile(@RequestBody Map<String, String> requestBody) {
    try {
        // Construir a URL com a rota para obter o IP do servidor DNS
        String dnsUrl = "http://localhost:8081/return";

        // Criar a solicitação HTTP GET para obter o IP
        HttpRequest dnsRequest = HttpRequest.newBuilder()
                .uri(new URI(dnsUrl))
                .GET()
                .build();

        // Enviar a solicitação ao servidor DNS e obter a resposta
        HttpResponse<String> dnsResponse = HttpClient.newHttpClient().send(dnsRequest, HttpResponse.BodyHandlers.ofString());

        // Extrair o IP do corpo da resposta do servidor DNS
        String ip = dnsResponse.body();

        // Construir a URL com o IP e a porta do destino
        String url = "http://"+ ip +"/emails/perfil";

        // Criar o corpo da requisição em formato JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(requestBody);

        // Criar a solicitação HTTP POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
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
=======
    /********************************************************************************************/
    @PostMapping("/profile")
    public String sendProfile(@RequestBody Map<String, String> requestBody) {
        try {
            // Construir a URL com a rota para obter o IP do servidor DNS
            String dnsUrl = "http://localhost:8081/return";

            // Criar a solicitação HTTP GET para obter o IP
            HttpRequest dnsRequest = HttpRequest.newBuilder()
                    .uri(new URI(dnsUrl))
                    .GET()
                    .build();

            // Enviar a solicitação ao servidor DNS e obter a resposta
            HttpResponse<String> dnsResponse = HttpClient.newHttpClient().send(dnsRequest, HttpResponse.BodyHandlers.ofString());

            // Extrair o IP do corpo da resposta do servidor DNS
            String ip = dnsResponse.body();

            // Construir a URL com o IP e a porta do destino
            String url = "http://"+ ip +"/emails/perfil";

            // Criar o corpo da requisição em formato JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // Criar a solicitação HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
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

    @GetMapping("/profile/obterArquivo/{filename}")
    public ResponseEntity<InputStreamResource> obterArquivo(@PathVariable String filename) {
        try {
            // Construir a URL com o IP e a porta do destino
            String url = "http://localhost:8181/obterArquivo/" + filename;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());

            // Criar um InputStream a partir da resposta
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.body().getBytes(StandardCharsets.UTF_8));

            // Retornar o arquivo como um recurso de InputStream
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




>>>>>>> ricardo-branch
}

