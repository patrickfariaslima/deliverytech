package com.deliverytech.delivery_api.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {
    
    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private boolean active;

    private LocalDateTime registeredAt;
}
