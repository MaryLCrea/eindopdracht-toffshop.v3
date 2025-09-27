package toff.novi.eindopdrachttoffshop.controllers;

import toff.novi.eindopdrachttoffshop.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (file.isEmpty()) {
                response.put("error", "Geen bestand geselecteerd");
                return ResponseEntity.badRequest().body(response);
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("error", "Alleen afbeeldingen toegestaan");
                return ResponseEntity.badRequest().body(response);
            }

            if (file.getSize() > 5 * 1024 * 1024) {
                response.put("error", "Bestand te groot (max 5MB)");
                return ResponseEntity.badRequest().body(response);
            }

            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = "/files/" + fileName;

            response.put("fileName", fileName);
            response.put("originalName", file.getOriginalFilename());
            response.put("fileDownloadUri", fileDownloadUri);
            response.put("fileType", file.getContentType());
            response.put("size", file.getSize());
            response.put("message", "Bestand succesvol geüpload");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Upload gefaald: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/products/{id}/upload-image")
    public ResponseEntity<Map<String, Object>> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (file.isEmpty()) {
                response.put("error", "Geen bestand geselecteerd");
                return ResponseEntity.badRequest().body(response);
            }

            String fileName = fileStorageService.storeFile(file);

            response.put("productId", id);
            response.put("fileName", fileName);
            response.put("message", "Bestand succesvol aan product gekoppeld");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Upload gefaald: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {
            Resource resource = fileStorageService.loadFileAsResource(fileName);

            String contentType;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException e) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{fileName:.+}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String fileName) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean deleted = fileStorageService.deleteFile(fileName);
            if (deleted) {
                response.put("message", "Bestand succesvol verwijderd");
                response.put("fileName", fileName);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Bestand niet gevonden");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            response.put("error", "Verwijderen gefaald: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getFileInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("uploadDirectory", fileStorageService.getFileStoragePath());
        response.put("maxFileSize", "5MB");
        response.put("allowedTypes", new String[]{"image/jpeg", "image/png", "image/gif", "image/webp"});
        response.put("message", "File upload informatie");

        return ResponseEntity.ok(response);
    }
}
