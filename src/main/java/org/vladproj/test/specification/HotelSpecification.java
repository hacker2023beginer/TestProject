package org.vladproj.test.specification;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.vladproj.test.entity.Hotel;

public class HotelSpecification {
    private HotelSpecification() {
    }

    public static Specification<Hotel> nameContains(String name) {
        return (root, query, cb) ->
                name == null
                        ? null
                        : cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Hotel> brandContains(String brand) {
        return (root, query, cb) ->
                brand == null
                        ? null
                        : cb.like(
                        cb.lower(root.get("brand")),
                        "%" + brand.toLowerCase() + "%"
                );
    }

    public static Specification<Hotel> cityEquals(String city) {
        return (root, query, cb) ->
                city == null
                        ? null
                        : cb.equal(
                        cb.lower(root.get("address").get("city")),
                        city.toLowerCase()
                );
    }

    public static Specification<Hotel> countryEquals(String country) {
        return (root, query, cb) ->
                country == null
                        ? null
                        : cb.equal(
                        cb.lower(root.get("address").get("country")),
                        country.toLowerCase()
                );
    }

    public static Specification<Hotel> hasAmenity(String amenity) {
        return (root, query, cb) -> {
            if (amenity == null || amenity.isBlank()) {
                return null;
            }

            Join<Hotel, String> amenities =
                    root.join("amenities");

            return cb.equal(
                    cb.lower(amenities),
                    amenity.toLowerCase()
            );
        };
    }
}
