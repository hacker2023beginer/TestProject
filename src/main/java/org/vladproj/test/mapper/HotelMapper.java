package org.vladproj.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vladproj.test.dto.CreateHotelRequestDto;
import org.vladproj.test.dto.HotelDto;
import org.vladproj.test.entity.Address;
import org.vladproj.test.entity.Hotel;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    String HOUSE_NUMBER_STREET_SEPARATOR = " ";
    String STREET_CITY_POST_CODE_COUNTRY_SEPARATOR = " ";

    Hotel toEntity(CreateHotelRequestDto request);

    @Mapping(source = "address", target = "address")
    @Mapping(source = "contacts.phone", target = "phone")
    HotelDto toDto(Hotel hotel);

    default String map(Address address) {
        if (address == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(address.getHouseNumber());
        sb.append(HOUSE_NUMBER_STREET_SEPARATOR);
        sb.append(address.getStreet());
        sb.append(STREET_CITY_POST_CODE_COUNTRY_SEPARATOR);
        sb.append(address.getCity());
        sb.append(STREET_CITY_POST_CODE_COUNTRY_SEPARATOR);
        sb.append(address.getPostCode());
        sb.append(STREET_CITY_POST_CODE_COUNTRY_SEPARATOR);
        sb.append(address.getCountry());
        return sb.toString();
    }

}
