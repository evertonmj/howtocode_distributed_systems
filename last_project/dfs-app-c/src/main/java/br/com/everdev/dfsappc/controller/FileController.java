package br.com.everdev.dfsappc.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.everdev.dfsappc.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        try {

            String filename = fileService.saveFile(file);
            return ResponseEntity.ok(filename);

        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {

        try {

            byte[] fileContent = fileService.getFile(filename);
            return ResponseEntity.ok(fileContent);

        } catch (FileNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
