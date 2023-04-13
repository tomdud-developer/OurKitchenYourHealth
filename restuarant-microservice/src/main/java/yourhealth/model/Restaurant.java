package yourhealth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Restaurant {
    @MongoId
    private String id;
    private String name;
    private Address address;

}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Address {
    private String street;
    private String houseNumber;
    private String flatNumber;
    private String city;
    private String postalCode;
    private String country;
}
