package com.deliverytech.delivery_api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDTO {
    private String name;
    private String category;
    private String address; 
    private String phoneNumber;
    private String rating;
    private String deliveryFee;
    private boolean active;
}
