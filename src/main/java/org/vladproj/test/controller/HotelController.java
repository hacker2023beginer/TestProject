package org.vladproj.test.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vladproj.test.dto.CreateHotelRequestDto;
import org.vladproj.test.dto.HotelDto;
import org.vladproj.test.entity.Hotel;
import org.vladproj.test.service.HotelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDto>> getHotel() {
        List<HotelDto> hotelDtoList = hotelService.getHotelDtos();
        return ResponseEntity.ok(hotelDtoList);
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id){
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenity
    ) {
        return ResponseEntity.ok(hotelService.search(
                name,
                brand,
                city,
                country,
                amenity
        ));
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelDto> createHotel(@RequestBody @Valid CreateHotelRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelService.create(request));
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelDto> addAmenities(
            @PathVariable Long id,
            @RequestBody @Valid @NotEmpty List<@NotBlank String> amenities
    ) {
        return ResponseEntity.ok(hotelService.addAmenities(id, amenities));
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(
            @PathVariable String param
    ) {
        return ResponseEntity.ok(hotelService.getHistogram(param));
    }
}
