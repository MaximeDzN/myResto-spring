package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Image;
import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.repos.ImageRepository;
import eu.ensup.my_resto.repos.ItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FileService fileService;

    @Value("${upload.path}")
    public String uploadPath;

    Random random = new Random();


    public List<ItemDTO> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(item -> mapToDTO(item, new ItemDTO()))
                .collect(Collectors.toList());
    }

    public ItemDTO get(final Long id) {
        return itemRepository.findById(id)
                .map(item -> mapToDTO(item, new ItemDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ItemDTO itemDTO) {
        final Item item = new Item();
        mapToEntity(itemDTO, item);
        if (item.getImage() != null) {
            String filename = randomString();
            fileService.saveImage(item.getImage(), filename);
            item.getImage().setPath(String.format("%s/%s", uploadPath, filename));
            imageRepository.save(item.getImage());
        }

        return itemRepository.save(item).getId();
    }

    public void update(final Long id, final ItemDTO itemDTO) {
        final Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(itemDTO, item);
        itemRepository.save(item);
    }

    public void delete(final Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            itemRepository.deleteById(id);
            fileService.deleteImage(item.get().getImage());
        }
    }

    private ItemDTO mapToDTO(final Item item, final ItemDTO itemDTO) {
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setCategory(item.getCategory());
        itemDTO.setImage(item.getImage());
        return itemDTO;
    }

    private Item mapToEntity(final ItemDTO itemDTO, final Item item) {
        item.setName(itemDTO.getName());
        item.setQuantity(itemDTO.getQuantity());
        item.setDescription(itemDTO.getDescription());
        item.setCategory(itemDTO.getCategory());
        item.setPrice(itemDTO.getPrice());
        item.setImage(itemDTO.getImage());
        return item;
    }

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
