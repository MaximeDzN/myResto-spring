package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Image;
import eu.ensup.my_resto.model.ImageDTO;
import eu.ensup.my_resto.repos.ImageRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<ImageDTO> findAll() {
        return imageRepository.findAll()
                .stream()
                .map(image -> mapToDTO(image, new ImageDTO()))
                .collect(Collectors.toList());
    }

    public ImageDTO get(final Long id) {
        return imageRepository.findById(id)
                .map(image -> mapToDTO(image, new ImageDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ImageDTO imageDTO) {
        final Image image = new Image();
        mapToEntity(imageDTO, image);
        return imageRepository.save(image).getId();
    }

    public void update(final Long id, final ImageDTO imageDTO) {
        final Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(imageDTO, image);
        imageRepository.save(image);
    }

    public void delete(final Long id) {
        imageRepository.deleteById(id);
    }

    private ImageDTO mapToDTO(final Image image, final ImageDTO imageDTO) {
        imageDTO.setId(image.getId());
        imageDTO.setPath(image.getPath());
        return imageDTO;
    }

    private Image mapToEntity(final ImageDTO imageDTO, final Image image) {
        image.setPath(imageDTO.getPath());
        return image;
    }

}
