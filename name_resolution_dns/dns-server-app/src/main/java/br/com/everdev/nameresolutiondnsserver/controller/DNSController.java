package br.com.everdev.nameresolutiondnsserver.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dns")
public class DNSController {
    private WebClient webClient = null;

    public DNSController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8761").build();
    }

    @GetMapping("/applications")
    public Mono<List<String>> getApplications() {
        return webClient.get()
                .uri("/eureka/apps")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    return List.of(response.split("\n"));
                });
    }

}