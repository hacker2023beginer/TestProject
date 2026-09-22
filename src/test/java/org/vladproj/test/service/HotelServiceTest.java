package org.vladproj.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.vladproj.test.dto.CreateHotelRequestDto;
import org.vladproj.test.dto.HotelDto;
import org.vladproj.test.entity.Hotel;
import org.vladproj.test.exception.HotelServiceException;
import org.vladproj.test.mapper.HotelMapper;
import org.vladproj.test.repository.HotelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void getHotelDtos_shouldReturnDtos() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        HotelDto dto = new HotelDto();
        dto.setId(1L);

        when(hotelRepository.findAll())
                .thenReturn(List.of(hotel));

        when(hotelMapper.toDto(hotel))
                .thenReturn(dto);

        List<HotelDto> result = hotelService.getHotelDtos();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));

        verify(hotelRepository).findAll();
        verify(hotelMapper).toDto(hotel);
    }

    @Test
    void getHotelById_shouldReturnHotel_whenExists() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(1L);

        assertEquals(hotel, result);

        verify(hotelRepository).findById(1L);
    }

    @Test
    void getHotelById_shouldThrowException_whenNotExists() {
        when(hotelRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                HotelServiceException.class,
                () -> hotelService.getHotelById(1L)
        );

        verify(hotelRepository).findById(1L);
    }

    @Test
    void search_shouldReturnHotels() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        when(hotelRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(hotel));

        List<Hotel> result = hotelService.search(
                "Hilton",
                null,
                "Minsk",
                null,
                "Pool"
        );

        assertEquals(1, result.size());
        assertEquals(hotel, result.get(0));

        verify(hotelRepository).findAll(any(Specification.class));
    }

    @Test
    void create_shouldMapSaveAndReturnDto() {
        CreateHotelRequestDto request = new CreateHotelRequestDto();

        Hotel hotel = new Hotel();
        Hotel savedHotel = new Hotel();
        savedHotel.setId(1L);

        HotelDto dto = new HotelDto();
        dto.setId(1L);

        when(hotelMapper.toEntity(request))
                .thenReturn(hotel);

        when(hotelRepository.save(hotel))
                .thenReturn(savedHotel);

        when(hotelMapper.toDto(savedHotel))
                .thenReturn(dto);

        HotelDto result = hotelService.create(request);

        assertEquals(dto, result);

        verify(hotelMapper).toEntity(request);
        verify(hotelRepository).save(hotel);
        verify(hotelMapper).toDto(savedHotel);
    }

    @Test
    void addAmenities_shouldAddAmenities() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setAmenities(new ArrayList<>());

        HotelDto dto = new HotelDto();
        dto.setId(1L);

        when(hotelRepository.findById(1L))
                .thenReturn(Optional.of(hotel));

        when(hotelRepository.save(hotel))
                .thenReturn(hotel);

        when(hotelMapper.toDto(hotel))
                .thenReturn(dto);

        List<String> amenities = List.of(
                "Pool",
                "WiFi"
        );

        HotelDto result = hotelService.addAmenities(1L, amenities);

        assertEquals(dto, result);
        assertEquals(
                List.of("Pool", "WiFi"),
                hotel.getAmenities()
        );

        verify(hotelRepository).findById(1L);
        verify(hotelRepository).save(hotel);
        verify(hotelMapper).toDto(hotel);
    }

    @Test
    void addAmenities_shouldThrowException_whenHotelNotFound() {
        when(hotelRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                HotelServiceException.class,
                () -> hotelService.addAmenities(
                        1L,
                        List.of("Pool")
                )
        );

        verify(hotelRepository).findById(1L);
        verify(hotelRepository, never()).save(any());
    }

    @Test
    void getHistogram_shouldReturnBrandHistogram() {
        when(hotelRepository.countByBrand())
                .thenReturn(List.of(
                        new Object[]{"Hilton", 3L},
                        new Object[]{"Marriott", 2L}
                ));

        Map<String, Long> result =
                hotelService.getHistogram("brand");

        assertEquals(2, result.size());
        assertEquals(3L, result.get("Hilton"));
        assertEquals(2L, result.get("Marriott"));

        verify(hotelRepository).countByBrand();
    }

    @Test
    void getHistogram_shouldReturnCityHistogram() {
        when(hotelRepository.countByCity())
                .thenReturn(List.of(
                        new Object[]{"Minsk", 5L},
                        new Object[]{"Brest", 2L}
                ));

        Map<String, Long> result =
                hotelService.getHistogram("city");

        assertEquals(5L, result.get("Minsk"));
        assertEquals(2L, result.get("Brest"));

        verify(hotelRepository).countByCity();
    }

    @Test
    void getHistogram_shouldReturnCountryHistogram() {
        when(hotelRepository.countByCountry())
                .thenReturn(List.of(
                        new Object[]{"Belarus", 5L},
                        new Object[]{"Poland", 2L}
                ));

        Map<String, Long> result =
                hotelService.getHistogram("country");

        assertEquals(5L, result.get("Belarus"));
        assertEquals(2L, result.get("Poland"));
    }

    @Test
    void getHistogram_shouldReturnAmenitiesHistogram() {
        when(hotelRepository.countByAmenity())
                .thenReturn(List.of(
                        new Object[]{"Pool", 3L},
                        new Object[]{"WiFi", 5L}
                ));

        Map<String, Long> result =
                hotelService.getHistogram("amenities");

        assertEquals(3L, result.get("Pool"));
        assertEquals(5L, result.get("WiFi"));
    }

    @Test
    void getHistogram_shouldThrowExceptionForUnknownParameter() {
        assertThrows(
                IllegalArgumentException.class,
                () -> hotelService.getHistogram("unknown")
        );

        verifyNoInteractions(hotelRepository);
    }
}