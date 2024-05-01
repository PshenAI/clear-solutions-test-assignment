package ai.pshenai.testassignment.user.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO {
    private String country;
    private String city;
    private String street;
    private String zip;

    public static UserAddressDTO getMockUserAddressObject() {
        UserAddressDTO address = new UserAddressDTO();
        address.setCountry("Ukraine");
        address.setCity("Kyiv");
        address.setStreet("Independency Square");

        return address;
    }
}
