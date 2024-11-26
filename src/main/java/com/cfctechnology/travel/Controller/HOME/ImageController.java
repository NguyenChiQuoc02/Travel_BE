package com.cfctechnology.travel.Controller.HOME;

import com.cfctechnology.travel.Utils.FileUploadImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping("/viewImage/{name}")
    public ResponseEntity<?> viewImage(@PathVariable("name") String name) {
        FileUploadImage image = new FileUploadImage();

        Resource resource = null;
        try {
            resource = image.getFileAsResource(name);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = null;
        try (InputStream inputStream = resource.getInputStream()) {
            contentType = URLConnection.guessContentTypeFromStream(inputStream);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
