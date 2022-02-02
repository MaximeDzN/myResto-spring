package eu.ensup.my_resto.service;

import eu.ensup.my_resto.domain.Item;
import eu.ensup.my_resto.domain.User;
import eu.ensup.my_resto.model.ItemDTO;
import eu.ensup.my_resto.model.Roles;
import eu.ensup.my_resto.repos.ItemRepository;
import eu.ensup.my_resto.repos.UserRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;


    @Test
    void get() {
        Item testItemDto = new Item();
        testItemDto.setName("burger");
        testItemDto.setPrice(25.2);
        testItemDto.setCategory("desert");
        testItemDto.setQuantity(56) ;
        testItemDto.setId(321L);

        itemRepository.save(testItemDto);
        // GIVEN
       // when(itemService.get(321L)).thenReturn(Optional.of(testItem));
        // WHEN
        final String result = itemService.get(321L).toString();
        MatcherAssert.assertThat("Test fail : ", result, Matchers.equalTo(testItemDto.toString()));
        // THEN
        verify(itemRepository).findById(321L);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}