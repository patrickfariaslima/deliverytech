package com.deliverytech.delivery_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[1-9]{2}(?:9[0-9]{8}|[2-5][0-9]{7})$", message = "Invalid phone number format. Format: DDNNNNNNNNN")
    private String phoneNumber;

    @Size(min= 5, message = "Address must be at least 5 characters long")
    private String address;
}
