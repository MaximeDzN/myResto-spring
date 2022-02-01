package io.bootify.my_app.item;

import io.bootify.my_app.image.Image;
import io.bootify.my_app.image.ImageRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    public ItemService(final ItemRepository itemRepository, final ImageRepository imageRepository) {
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
    }

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
        return itemRepository.save(item).getId();
    }

    public void update(final Long id, final ItemDTO itemDTO) {
        final Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(itemDTO, item);
        itemRepository.save(item);
    }

    public void delete(final Long id) {
        itemRepository.deleteById(id);
    }

    private ItemDTO mapToDTO(final Item item, final ItemDTO itemDTO) {
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setImage(item.getImage() == null ? null : item.getImage().getId());
        return itemDTO;
    }

    private Item mapToEntity(final ItemDTO itemDTO, final Item item) {
        item.setName(itemDTO.getName());
        item.setQuantity(itemDTO.getQuantity());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        if (itemDTO.getImage() != null && (item.getImage() == null || !item.getImage().getId().equals(itemDTO.getImage()))) {
            final Image image = imageRepository.findById(itemDTO.getImage())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "image not found"));
            item.setImage(image);
        }
        return item;
    }

}
