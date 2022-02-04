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


/**
 * The type Image service.
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    /**
     * Instantiates a new Image service.
     *
     * @param imageRepository the image repository
     */
    public ImageService(final ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<ImageDTO> findAll() {
        return imageRepository.findAll()
                .stream()
                .map(image -> mapToDTO(image, new ImageDTO()))
                .collect(Collectors.toList());
    }

    /**
     * Get image dto.
     *
     * @param id the id
     * @return the image dto
     */
    public ImageDTO get(final Long id) {
        return imageRepository.findById(id)
                .map(image -> mapToDTO(image, new ImageDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Create long.
     *
     * @param imageDTO the image dto
     * @return the long
     */
    public Long create(final ImageDTO imageDTO) {
        final Image image = new Image();
        mapToEntity(imageDTO, image);
        return imageRepository.save(image).getId();
    }

    /**
     * Update.
     *
     * @param id       the id
     * @param imageDTO the image dto
     */
    public void update(final Long id, final ImageDTO imageDTO) {
        final Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(imageDTO, image);
        imageRepository.save(image);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
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
