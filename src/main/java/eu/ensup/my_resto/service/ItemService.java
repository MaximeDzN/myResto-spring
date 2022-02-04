package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.repos.ImageRepository;
import eu.ensup.my_resto.repos.ItemRepository;
import eu.ensup.my_resto.repos.Projections.MapProjection;
import eu.ensup.my_resto.service.exception.FileNotDeleted;
import eu.ensup.my_resto.service.exception.FileNotSaved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * The type Item service.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FileService fileService;

    /**
     * The Upload path.
     */
    @Value("${upload.path}")
    public String uploadPath;

    /**
     * The Random.
     */
    Random random = new Random();


    /**
     * Find all list.
     *
     * @return the list
     */
    public List<ItemDTO> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get item dto.
     *
     * @param id the id
     * @return the item dto
     */
    public ItemDTO get(final Long id) {
        return itemRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * Create long.
     *
     * @param itemDTO the item dto
     * @return the long
     * @throws FileNotSaved the file not saved
     */
    public Long create(final ItemDTO itemDTO) throws FileNotSaved {
        Item item = mapToEntity(itemDTO);
        if (item.getImage() != null) {
            String filename = randomString();
            fileService.saveImage(item.getImage(), filename);
            item.getImage().setPath(filename);
            imageRepository.save(item.getImage());
        }
        return itemRepository.save(item).getId();
    }

    /**
     * Update.
     *
     * @param id      the id
     * @param itemDTO the item dto
     */
    public void update(final Long id, final ItemDTO itemDTO) {
        itemRepository.save(mapToEntity(itemDTO));
    }

    /**
     * Delete.
     *
     * @param id the id
     * @throws FileNotDeleted the file not deleted
     */
    public void delete(final Long id) throws FileNotDeleted {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            itemRepository.deleteById(id);
            fileService.deleteImage(item.get().getImage());
        }
    }

    /**
     * Get nb item category list.
     *
     * @return the list
     */
    public List<MapProjection> getNbItemCategory(){
        return itemRepository.findNbItemCategory();
    }

    private ItemDTO mapToDTO(final Item item) {
        return ItemDTO.builder()
        .id(item.getId())
        .name(item.getName())
        .quantity(item.getQuantity())
        .description(item.getDescription())
        .price(item.getPrice())
        .category(item.getCategory())
        .image(item.getImage()).build();
    }

    private Item mapToEntity(final ItemDTO itemDTO) {
        return Item.builder()
        .name(itemDTO.getName())
        .quantity(itemDTO.getQuantity())
        .description(itemDTO.getDescription())
        .category(itemDTO.getCategory())
        .price(itemDTO.getPrice())
        .image(itemDTO.getImage()).build();
    }

    /**
     * Random string string.
     *
     * @return the string
     */
    public String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return String.format("%s.png", generatedString);
    }

}
