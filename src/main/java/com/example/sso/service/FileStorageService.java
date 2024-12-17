package com.example.sso.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

        private final String uploadDir = "src/main/resources/static/uploads/";

        public String storeFile(MultipartFile file) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Stored file at: " + filePath.toString());

                // Return relative path to file without '/uploads/' prefix again
                return "uploads/" + fileName; // Relative path (no leading '/')
            } catch (IOException e) {
                throw new RuntimeException("Could not store the file. Error!!: " + e.getMessage());
            }
        }


}