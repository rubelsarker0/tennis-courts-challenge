package com.tenniscourts.guests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = GuestService.class)
public class GuestServiceTest {

    @InjectMocks
    GuestService guestService;

    @Mock
    GuestMapper guestMapper;

    @Mock
    GuestRepository guestRepository;

    @Test
    public void testFindGuestByName(){
        GuestDTO gDto = new GuestDTO();
        gDto.setName("ABCD");
        gDto.setId(Long.valueOf("1000"));

        List<GuestDTO> guestDTOs = new ArrayList<>();
        guestDTOs.add(gDto);

        Mockito.when(guestMapper.map(guestRepository.findByName(Mockito.any()))).thenReturn((guestDTOs));

        List<GuestDTO> dtos = guestService.findGuestByName("ABCD");
        Assert.assertEquals(guestDTOs.size(), dtos.size());
    }
}
