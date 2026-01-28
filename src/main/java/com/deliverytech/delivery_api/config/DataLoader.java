package com.deliverytech.delivery_api.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.model.OrderedItem;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.*;

@Configuration
public class DataLoader {

    private final OrderedItemRepository orderedItemRepository;

    DataLoader(OrderedItemRepository orderedItemRepository) {
        this.orderedItemRepository = orderedItemRepository;
    }
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
            r2.setName("Burger House");
            r2.setCategory("Hamburgueria");
            r2.setAddress("Rua Augusta, 500 - São Paulo/SP");
            r2.setPhoneNumber("11999998888");
            r2.setRating(new BigDecimal("4.0"));
            r2.setDeliveryFee(new BigDecimal("8.00"));
            r2.setActive(true);

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

            List<Product> products = new ArrayList<>();
            products.add(p1);
            products.add(p2);
            productRepository.saveAll(products);

            Order order1 = new Order();
            order1.setClient(client1);
            order1.setRestaurant(r1);
            order1.setStatus(OrdersStatus.PENDING);
            order1.setDeliveryAddress(client1.getAddress());
            order1.setOrderNumber("PED1234567890");
            order1.setTotal(BigDecimal.ZERO);

            orderRepository.save(order1);

            Order order2 = new Order();
            order2.setClient(client2);
            order2.setRestaurant(r2);
            order2.setStatus(OrdersStatus.PENDING);
            order2.setDeliveryAddress(client2.getAddress());
            order2.setOrderNumber("PED1234567891");
            order2.setTotal(BigDecimal.ZERO);

            orderRepository.save(order2);


            OrderedItem item1 = new OrderedItem();
            item1.setProduct(p1);
            item1.setOrder(order1);
            item1.setQuantity(2);
            item1.setItemPrice(p1.getPrice());
            item1.setTotal(p1.getPrice().multiply(new BigDecimal(item1.getQuantity())));

            orderedItemRepository.save(item1);

            OrderedItem item2 = new OrderedItem();
            item2.setProduct(p2);
            item2.setOrder(order2);
            item2.setQuantity(5);
            item2.setItemPrice(p2.getPrice());
            item2.setTotal(p2.getPrice().multiply(new BigDecimal(item2.getQuantity())));

            orderedItemRepository.save(item2);

            System.out.println("Carregamento de dados concluído.");
        };
    }
}
