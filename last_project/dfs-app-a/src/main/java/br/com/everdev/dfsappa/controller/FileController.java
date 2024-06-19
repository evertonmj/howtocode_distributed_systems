package br.com.everdev.dfsappa.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import br.com.everdev.dfsappa.service.NodeSelectionService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NodeSelectionService nodeSelectionService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        String nodeName = nodeSelectionService.selectRandomNode();
        String url = "http://" + nodeName + "/files";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("file", new ByteArrayResource(file.getBytes()) {
            
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        List<String> nodes = Arrays.asList("dfs-app-b", "dfs-app-c");
        for (String node : nodes) {
            try {

                String url = "http://" + node + "/files/" + filename;

                ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    return response;

                }

            } 
            catch (Exception e) {
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}