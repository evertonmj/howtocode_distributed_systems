package com.dfsa.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.UUID;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value="/dfs-a-upload", produces = {"application/json"})
public class Controller {

    public String dfsNodeBUrl = "http://localhost:8088/dfs-b-upload";
    public String dfsNodeCUrl = "http://localhost:8087/dfs-c-upload";

    public final Random random = new Random();

    @GetMapping("/obterArquivo/{filename}")
    public String obterArquivo(@PathVariable String filename) {
        String url = (random.nextBoolean() ? dfsNodeBUrl : dfsNodeCUrl) + "/obterArquivo/" + filename;
        try {
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

    @PostMapping(path = "/salvarArquivo/{filename}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String salvarArquivo(@PathVariable String filename, @RequestParam("file") MultipartFile file) {
        String url = (random.nextBoolean() ? dfsNodeBUrl : dfsNodeCUrl) + "/salvarArquivo/" + filename;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(file.getBytes()))
                    .header("Content-Type", "multipart/form-data")
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
    }
}


}
