package org.vladproj.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.vladproj.test.dto.CreateHotelRequestDto;
import org.vladproj.test.dto.HotelDto;
import org.vladproj.test.entity.Hotel;
import org.vladproj.test.service.HotelService;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HotelService hotelService;

    @Test
    void getHotels_shouldReturnHotels() throws Exception {

        HotelDto dto = new HotelDto();
        dto.setId(1L);
        dto.setName("DoubleTree by Hilton Minsk");

        when(hotelService.getHotelDtos())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("DoubleTree by Hilton Minsk"));
    }

    @Test
    void getHotelById_shouldReturnHotel() throws Exception {

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("DoubleTree by Hilton Minsk");

        when(hotelService.getHotelById(1L))
                .thenReturn(hotel);

        mockMvc.perform(get("/property-view/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("DoubleTree by Hilton Minsk"));
    }

    @Test
    void search_shouldReturnHotels() throws Exception {

        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("DoubleTree by Hilton Minsk");

        when(hotelService.search(
                eq(null),
                eq(null),
                eq("Minsk"),
                eq(null),
                eq(null)
        )).thenReturn(List.of(hotel));

        mockMvc.perform(
                        get("/property-view/search")
                                .param("city", "Minsk")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("DoubleTree by Hilton Minsk"));
    }

    @Test
    void getHistogram_shouldReturnHistogram() throws Exception {

        when(hotelService.getHistogram("brand"))
                .thenReturn(Map.of(
                        "Hilton", 3L,
                        "Marriott", 2L
                ));

        mockMvc.perform(
                        get("/property-view/histogram/brand")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Hilton").value(3))
                .andExpect(jsonPath("$.Marriott").value(2));
    }

    @Test
    void createHotel_shouldReturnCreatedHotel() throws Exception {

        HotelDto dto = new HotelDto();
        dto.setId(1L);
        dto.setName("DoubleTree by Hilton Minsk");

        when(hotelService.create(any(CreateHotelRequestDto.class)))
                .thenReturn(dto);

        String json = """
                {
                  "name": "DoubleTree by Hilton Minsk",
                  "description": "Luxury hotel in Minsk",
                  "brand": "Hilton",
                  "address": {
                    "houseNumber": "9",
                    "street": "Pobediteley Avenue",
                    "city": "Minsk",
                    "country": "Belarus",
                    "postCode": "220004"
                  },
                  "contacts": {
                    "phone": "+375 17 309-80-00",
                    "email": "minsk@hilton.com"
                  },
                  "arrivalTime": {
                    "checkIn": "14:00:00",
                    "checkOut": "12:00:00"
                  }
                }
                """;

        mockMvc.perform(
                        post("/property-view/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("DoubleTree by Hilton Minsk"));
    }

    @Test
    void createHotel_shouldReturnBadRequest_whenNameIsMissing()
            throws Exception {

        String json = """
                {
                  "description": "Luxury hotel in Minsk",
                  "brand": "Hilton",
                  "address": {
                    "houseNumber": "9",
                    "street": "Pobediteley Avenue",
                    "city": "Minsk",
                    "country": "Belarus",
                    "postCode": "220004"
                  },
                  "contacts": {
                    "phone": "+375 17 309-80-00",
                    "email": "minsk@hilton.com"
                  }
                }
                """;

        mockMvc.perform(
                        post("/property-view/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createHotel_shouldReturnBadRequest_whenAddressIsMissing()
            throws Exception {

        String json = """
                {
                  "name": "DoubleTree by Hilton Minsk",
                  "brand": "Hilton",
                  "contacts": {
                    "phone": "+375 17 309-80-00",
                    "email": "minsk@hilton.com"
                  }
                }
                """;

        mockMvc.perform(
                        post("/property-view/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest());
    }
}
