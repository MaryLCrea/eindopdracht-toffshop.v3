package toff.novi.eindopdrachttoffshop.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {
    @NotBlank
    @Size(min = 3, max = 50)
    public String name;
    @Email
    @Column(unique = true)
    public String email;
    @Column(unique = true)
    public int phone;
    public String password;


    public String getName() {
            return null;
    }

    public String getEmail() {
        return null;
    }

    public int getPhone() {
        return 0;
    }

    public String getPassword() {
        return null;
    }
}
