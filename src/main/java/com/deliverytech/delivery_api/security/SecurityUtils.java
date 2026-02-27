package com.deliverytech.delivery_api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.deliverytech.delivery_api.enums.UserRole;
import com.deliverytech.delivery_api.model.User;

@Component
public class SecurityUtils {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    public static Long getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    public static String getCurrentUserEmail() {
        User user = getCurrentUser();
        return user != null ? user.getEmail() : null;
    }

    public static Long getCurretUserRestaurantId() {
        User user = getCurrentUser();
        return user != null ? user.getRestaurantId() : null;
    }

    public static boolean hasRole(UserRole role) {
        User user = getCurrentUser();
        return user != null && user.getRole() == role;
    }

    public static boolean isAdmin() {
        return hasRole(UserRole.ADMIN);
    }

    public static boolean isRestaurant() {
        return hasRole(UserRole.RESTAURANT);
    }

    public static boolean isClient() {
        return hasRole(UserRole.CLIENT);
    }

    public static boolean isDelivery() {
        return hasRole(UserRole.DELIVERY);
    }
}
