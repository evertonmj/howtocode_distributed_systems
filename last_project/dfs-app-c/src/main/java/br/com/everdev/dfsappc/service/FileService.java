package br.com.everdev.dfsappc.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final String FILE_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String versionedFilename = generateVersionedFilename(originalFilename);
        Files.copy(file.getInputStream(), Paths.get(FILE_DIR + versionedFilename));

        return versionedFilename;
    }

    public byte[] getFile(String filename) throws IOException {

        Path path = Paths.get(FILE_DIR + filename);

        if (Files.exists(path)) {

            return Files.readAllBytes(path);
        }

        throw new FileNotFoundException("File not found");
    }

    private String generateVersionedFilename(String originalFilename) {

        String baseName = FilenameUtils.getBaseName(originalFilename);
        String extension = FilenameUtils.getExtension(originalFilename);
        int version = 1;
        String versionedFilename;

        do {
            versionedFilename = String.format("%s_v%d.%s", baseName, version, extension);
            version++;

        } while (Files.exists(Paths.get(FILE_DIR + versionedFilename)));

        return versionedFilename;
    }
}
