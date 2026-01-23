package com.deliverytech.delivery_api.config;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.repository.OrderRepository;
import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner initData(
        ClientRepository clientRepository,
        RestaurantRepository restaurantRepository,
        ProductRepository productRepository,
        OrderRepository orderRepository
    ) {
        return args -> {
            System.out.println("Iniciando carregamento de dados...");

            Client client1 = new Client();
            client1.setName("Raiel Landre");
            client1.setEmail("raiel@gmail.com");
            client1.setPhoneNumber("11987654321");
            client1.setAddress("Rua das Flores, 123, São Paulo, SP");
            client1.setActive(true);

            Client client2 = new Client();
            client2.setName("Giovanni de Carvalho");
            client2.setEmail("giovanni@gmail.com");
            client2.setPhoneNumber("11912345678");
            client2.setAddress("Avenida Brasil, 456, Rio de Janeiro, RJ");
            client2.setActive(true);

/*             clientRepository.save(client1);
            clientRepository.save(client2); */

            clientRepository.saveAll(Arrays.asList(client1, client2));

            Restaurant r1 = new Restaurant();
            r1.setName("Pizza");
            r1.setCategory("Pizzaria");
            r1.setAddress("Rua das Pizzas, 100, São Paulo, SP");
            r1.setPhoneNumber("11999998888");
            r1.setRating(new BigDecimal("4.5"));
            r1.setDeliveryFee(new BigDecimal("5.00"));
            r1.setActive(true);

            Restaurant r2 = new Restaurant();
            r1.setName("Burger House");
            r1.setCategory("Hamburgueria");
            r1.setAddress("Rua Augusta, 500 - São Paulo/SP");
            r1.setPhoneNumber("11999998888");
            r1.setRating(new BigDecimal("4.0"));
            r1.setDeliveryFee(new BigDecimal("8.00"));
            r1.setActive(true);

            restaurantRepository.saveAll(Arrays.asList(r1, r2));

            Product p1 = new Product();
            p1.setName("Pizza de Calabresa");
            p1.setDescription("Deliciosa pizza de calabresa com borda recheada");
            p1.setPrice(new BigDecimal("35.00"));
            p1.setCategory("Pizza");
            p1.setAvailable(true);
            p1.setRestaurant(r1);

            Product p2 = new Product();
            p2.setName("Hambúrguer Clássico");
            p2.setDescription("Hambúrguer com queijo, alface, tomate e molho especial");
            p2.setPrice(new BigDecimal("25.00"));
            p2.setCategory("Hambúrguer");
            p2.setAvailable(true);
            p2.setRestaurant(r2);

            productRepository.saveAll(Arrays.asList(p1, p2));
        };
    }
}
