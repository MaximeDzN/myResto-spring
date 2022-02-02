package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.model.ImageDTO;
import eu.ensup.my_resto.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class ImageController {

    private final ImageService imageService;

    @Value("${upload.path}")
    public String uploadPath;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        return ResponseEntity.ok(imageService.findAll());
    }

    @GetMapping("images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        byte[] file = Files.readAllBytes(Paths.get(String.format("%s/%s",uploadPath,filename)));
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(file);
    }

    @PostMapping
    public ResponseEntity<Long> createImage(@RequestBody @Valid final ImageDTO imageDTO) {
        return new ResponseEntity<>(imageService.create(imageDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateImage(@PathVariable final Long id,
            @RequestBody @Valid final ImageDTO imageDTO) {
        imageService.update(id, imageDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable final Long id) {
        imageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
