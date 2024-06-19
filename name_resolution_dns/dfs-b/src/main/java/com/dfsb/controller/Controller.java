package com.dfsb.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value="/dfs-b-upload", produces = {"application/json"})
public class Controller {
    private static final Map<String, byte[]> storage = new HashMap<>();

    @GetMapping("/obterArquivo/{filename}")
    public ResponseEntity<Resource> obterArquivo(@PathVariable String filename) {
        byte[] file = storage.get(filename);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayResource resource = new ByteArrayResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .contentLength(file.length)
                .body(resource);
    }

    @PostMapping("/salvarArquivo/{filename}")
    public ResponseEntity<String> salvarArquivo(@PathVariable String filename, @RequestParam("file") MultipartFile file) {
        try {
            // Salvar o arquivo usando sua lógica de armazenamento
            storage.put(filename, file.getBytes());
            return ResponseEntity.ok("File saved successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to save file");
        }
    }

    @GetMapping("/listarArquivos")
    public ResponseEntity<Map<String, String>> listarArquivos() {
        Map<String, String> fileList = storage.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new String(entry.getValue())
                ));
        return ResponseEntity.ok(fileList);
    }
}
