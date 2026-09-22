package org.vladproj.test.specification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.vladproj.test.entity.Address;
import org.vladproj.test.entity.Hotel;
import org.vladproj.test.repository.HotelRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class HotelSpecificationTest {

    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll();

        Hotel hotel = new Hotel();

        hotel.setName("DoubleTree by Hilton Minsk");
        hotel.setBrand("Hilton");

        Address address = new Address();
        address.setCity("Minsk");
        address.setCountry("Belarus");

        hotel.setAddress(address);
        hotel.setAmenities(
                new ArrayList<>(
                        List.of("Pool", "WiFi")
                )
        );

        hotelRepository.save(hotel);
    }

    @Test
    void shouldFindByName() {
        List<Hotel> result = hotelRepository.findAll(
                HotelSpecification.nameContains("hilton")
        );

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindByBrand() {
        List<Hotel> result = hotelRepository.findAll(
                HotelSpecification.brandContains("HILTON")
        );

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindByCity() {
        List<Hotel> result = hotelRepository.findAll(
                HotelSpecification.cityEquals("minsk")
        );

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindByCountry() {
        List<Hotel> result = hotelRepository.findAll(
                HotelSpecification.countryEquals("BELARUS")
        );

        assertEquals(1, result.size());
    }

    @Test
    void shouldFindByAmenity() {
        List<Hotel> result = hotelRepository.findAll(
                HotelSpecification.hasAmenity("wifi")
        );

        assertEquals(1, result.size());
    }

    @Test
    void shouldIgnoreNullParameters() {
        List<Hotel> result = hotelRepository.findAll(
                HotelSpecification.nameContains(null)
                        .and(HotelSpecification.cityEquals(null))
                        .and(HotelSpecification.hasAmenity(null))
        );

        assertEquals(1, result.size());
    }
}
