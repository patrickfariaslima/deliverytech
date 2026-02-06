package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;

public interface RevenueByCategoryProjection {
    String getCategory();
    BigDecimal getTotalRevenue();
}
