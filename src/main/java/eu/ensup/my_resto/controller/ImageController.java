package eu.ensup.my_resto.controller;

import eu.ensup.my_resto.model.ImageDTO;
import eu.ensup.my_resto.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequestMapping("images")
public class ImageController {

    Logger logger = LoggerFactory.getLogger(ImageController.class);


    @Autowired
    private  ImageService imageService;

    @Value("${upload.path}")
    public String uploadPath;


    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        return ResponseEntity.ok(imageService.findAll());
    }

    @GetMapping("{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        try {
            byte[] file = Files.readAllBytes(Paths.get(String.format("%s/%s",uploadPath,filename)));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(file);
        } catch (Exception e){
            logger.error(String.format("Canno't retrieve image %s",e.getMessage()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(Files.readAllBytes(new ClassPathResource("static/assets/placeholder.png").getFile().toPath()));
        }

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
