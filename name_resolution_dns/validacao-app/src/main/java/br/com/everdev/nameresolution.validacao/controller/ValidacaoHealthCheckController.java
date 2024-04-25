package br.com.everdev.nameresolution.validacao.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
import java.util.Map;

@RestController
public class ValidacaoHealthCheckController {
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    private List<Map<String, String>> emailsArmazenados = new ArrayList<>();

    @Value("${spring.application.name}")
    private String appName;

    //endereço para testar se a aplicação está funcional
    @GetMapping("/health")
    public String healthy() {
        return "Estou vivo e bem! Sou a app "+appName+" - " + LocalDateTime.now();
    }

    @PostMapping("/validacao")
    public String validarEmail(@RequestBody Map<String, String> requestBody) {
        try {
            // Extrair os dados do corpo da requisição
            String email = requestBody.get("email");
            String nome = requestBody.get("nome");
            String perfil = requestBody.get("perfil");



            for (Map<String, String> storedEmail : emailsArmazenados) {
                if (storedEmail.get("email").equals(email)) {
                    return "O email já cadastrado.";
                }
            }

            // Armazenar os dados em uma lista
            emailsArmazenados.add(Map.of(   "email", email, "nome", nome, "perfil", perfil));

            return "Email cadastrado com sucesso.";
        } catch (Exception e) {
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }
    @GetMapping("/emails")
    public List<Map<String, String>> getEmailsArmazenados() {
        return emailsArmazenados;
    }
    @PostMapping("/emails/perfil")
    public String getPerfilByEmail(@RequestBody Map<String, String> requestBody) {
        try {
            // Extrair o email do corpo da requisição JSON
            String email = requestBody.get("email");

            // Procurar o email na lista emailsArmazenados
            for (Map<String, String> storedEmail : emailsArmazenados) {
                if (storedEmail.containsKey("email") && storedEmail.get("email").equals(email)) {
                    // Retornar o perfil correspondente se encontrar o email
                    return storedEmail.get("perfil");
                }
            }

            // Se o email não for encontrado, retornar uma mensagem de erro
            return "Email não encontrado na lista.";
        } catch (Exception e) {
            // Em caso de exceção, retornar uma mensagem de erro
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }

}
