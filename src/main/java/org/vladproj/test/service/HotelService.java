package org.vladproj.test.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.vladproj.test.dto.CreateHotelRequestDto;
import org.vladproj.test.dto.HotelDto;
import org.vladproj.test.entity.Hotel;
import org.vladproj.test.exception.HotelServiceException;
import org.vladproj.test.mapper.HotelMapper;
import org.vladproj.test.repository.HotelRepository;
import org.vladproj.test.specification.HotelSpecification;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    public List<HotelDto> getHotelDtos() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toDto)
                .toList();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelServiceException("Hotel not found"));
    }

    public List<Hotel> search(
            String name,
            String brand,
            String city,
            String country,
            String amenity
    ) {
        Specification<Hotel> spec = Specification
                .where(HotelSpecification.nameContains(name))
                .and(HotelSpecification.brandContains(brand))
                .and(HotelSpecification.cityEquals(city))
                .and(HotelSpecification.countryEquals(country))
                .and(HotelSpecification.hasAmenity(amenity));

        return hotelRepository.findAll(spec);
    }

    public HotelDto create(CreateHotelRequestDto request) {
        Hotel hotel = hotelMapper.toEntity(request);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(savedHotel);
    }

    public HotelDto addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelServiceException("Hotel not found"));
        hotel.getAmenities().addAll(amenities);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(savedHotel);
    }

    public Map<String, Long> getHistogram(String param) {

        List<Object[]> result;

        switch (param) {
            case "brand" -> result = hotelRepository.countByBrand();
            case "city" -> result = hotelRepository.countByCity();
            case "country" -> result = hotelRepository.countByCountry();
            case "amenities" -> result = hotelRepository.countByAmenity();
            default -> throw new IllegalArgumentException("Unknown parameter");
        }

        return result.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }
}
