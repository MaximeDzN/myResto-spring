package eu.ensup.my_resto.rest;

import eu.ensup.my_resto.model.ImageDTO;
import eu.ensup.my_resto.service.ImageService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAllImages() {
        return ResponseEntity.ok(imageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable final Long id) {
        return ResponseEntity.ok(imageService.get(id));
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
