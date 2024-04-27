package ai.pshenai.testassignment.user.web;

import lombok.Data;

@Data
public class UserAddressDTO {
    private String country;
    private String city;
    private String street;
    private String zip;
}
