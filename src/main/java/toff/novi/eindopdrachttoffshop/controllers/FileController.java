package toff.novi.eindopdrachttoffshop.controllers;

import toff.novi.eindopdrachttoffshop.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {

            if (file.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Geen bestand geselecteerd");
                return ResponseEntity.badRequest().body(errorResponse);
            }


            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Alleen afbeeldingen zijn toegestaan");
                return ResponseEntity.badRequest().body(errorResponse);
            }


            if (file.getSize() > 5 * 1024 * 1024) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Bestand is te groot (max 5MB)");
                return ResponseEntity.badRequest().body(errorResponse);
            }


            String fileName = fileStorageService.storeFile(file);


            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")
                    .path(fileName)
                    .toUriString();

            Map<String, Object> response = new HashMap<>();
            response.put("fileName", fileName);
            response.put("originalName", file.getOriginalFilename());
            response.put("fileDownloadUri", fileDownloadUri);
            response.put("fileType", file.getContentType());
            response.put("size", file.getSize());
            response.put("message", "Bestand succesvol geüpload");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Upload gefaald: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {

            Resource resource = fileStorageService.loadFileAsResource(fileName);


            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {

            }

            if (contentType == null) {
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

    @DeleteMapping("/delete/{fileName:.+}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String fileName) {
        try {
            boolean deleted = fileStorageService.deleteFile(fileName);

            Map<String, Object> response = new HashMap<>();
            if (deleted) {
                response.put("message", "Bestand succesvol verwijderd");
                response.put("fileName", fileName);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Bestand niet gevonden");
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Verwijderen gefaald: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
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