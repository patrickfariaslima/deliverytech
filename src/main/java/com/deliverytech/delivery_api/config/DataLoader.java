package com.deliverytech.delivery_api.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
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
            log.info("Iniciando carregamento de dados...");

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

            Client client3 = new Client();
            client3.setName("Marina Rocha");
            client3.setEmail("marina@gmail.com");
            client3.setPhoneNumber("11922223333");
            client3.setAddress("Rua Nova, 789, Curitiba, PR");
            client3.setActive(true);

            clientRepository.saveAll(Arrays.asList(client1, client2, client3));

            String activeClients = String.join(
                System.lineSeparator(),
                clientRepository.findByActiveTrue().stream()
                    .map(client -> " - " + client.getId() + " | " + client.getName())
                    .toList()
            );
            log.info("\nClientes ativos:{}{}", System.lineSeparator(), activeClients);

            String clientsWithA = String.join(
                System.lineSeparator(),
                clientRepository.findByNameContainingIgnoreCase("a").stream()
                    .map(client -> " - " + client.getId() + " | " + client.getName())
                    .toList()
            );
            log.info("\nClientes com 'a' no nome:{}{}", System.lineSeparator(), clientsWithA);


            Restaurant r1 = new Restaurant();
            r1.setName("Pizza Mania");
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

            String activeRestaurants = String.join(
                System.lineSeparator(),
                restaurantRepository.findByActiveTrue().stream()
                    .map(restaurant -> " - " + restaurant.getId() + " | " + restaurant.getName())
                    .toList()
            );
            log.info("\nRestaurantes ativos:{}{}", System.lineSeparator(), activeRestaurants);

            String restaurantsByFee = String.join(
                System.lineSeparator(),
                restaurantRepository.findByDeliveryFeeLessThanEqual(new BigDecimal("6.00")).stream()
                    .map(restaurant -> " - " + restaurant.getId() + " | " + restaurant.getName())
                    .toList()
            );
            log.info("\nRestaurantes taxa <= 6:{}{}", System.lineSeparator(), restaurantsByFee);

            String topRestaurantsByName = String.join(
                System.lineSeparator(),
                restaurantRepository.findTop5ByOrderByNameAsc().stream()
                    .map(restaurant -> " - " + restaurant.getId() + " | " + restaurant.getName())
                    .toList()
            );
            log.info("\nTop 5 por nome:{}{}", System.lineSeparator(), topRestaurantsByName);

            String restaurantsByCategory = String.join(
                System.lineSeparator(),
                restaurantRepository.findByCategory("Pizzaria").stream()
                    .map(restaurant -> " - " + restaurant.getId() + " | " + restaurant.getName())
                    .toList()
            );
            log.info("\nRestaurantes por categoria Pizzaria:{}{}", System.lineSeparator(), restaurantsByCategory);

            String productsByCategory = String.join(
                System.lineSeparator(),
                productRepository.findByCategory("Pizza").stream()
                    .map(product -> " - " + product.getId() + " | " + product.getName())
                    .toList()
            );
            log.info("\nProdutos por categoria Pizza:{}{}", System.lineSeparator(), productsByCategory);

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

            Product p3 = new Product();
            p3.setName("Pizza Margherita");
            p3.setDescription("Mussarela, tomate e manjericão");
            p3.setPrice(new BigDecimal("32.00"));
            p3.setCategory("Pizza");
            p3.setAvailable(true);
            p3.setRestaurant(r1);

            Product p4 = new Product();
            p4.setName("Cheeseburger Duplo");
            p4.setDescription("Dois hambúrgueres, cheddar e molho");
            p4.setPrice(new BigDecimal("28.00"));
            p4.setCategory("Hambúrguer");
            p4.setAvailable(true);
            p4.setRestaurant(r2);

            Product p5 = new Product();
            p5.setName("Batata Frita");
            p5.setDescription("Porção grande");
            p5.setPrice(new BigDecimal("15.00"));
            p5.setCategory("Acompanhamento");
            p5.setAvailable(true);
            p5.setRestaurant(r2);

            List<Product> products = new ArrayList<>();
            products.add(p1);
            products.add(p2);
            products.addAll(Arrays.asList(p3, p4, p5));
            productRepository.saveAll(products);

            String availableProducts = String.join(
                System.lineSeparator(),
                productRepository.findByAvailableTrue().stream()
                    .map(product -> " - " + product.getId() + " | " + product.getName())
                    .toList()
            );
            log.info("\nProdutos disponíveis:{}{}", System.lineSeparator(), availableProducts);

            String productsByPrice = String.join(
                System.lineSeparator(),
                productRepository.findByPriceLessThanEqual(new BigDecimal("30.00")).stream()
                    .map(product -> " - " + product.getId() + " | " + product.getName())
                    .toList()
            );
            log.info("\nProdutos preço <= 30:{}{}", System.lineSeparator(), productsByPrice);

            String productsByRestaurant = String.join(
                System.lineSeparator(),
                productRepository.findByRestaurantId(r1.getId()).stream()
                    .map(product -> " - " + product.getId() + " | " + product.getName())
                    .toList()
            );
            log.info("\nProdutos do restaurante r1:{}{}", System.lineSeparator(), productsByRestaurant);


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

            String lastOrders = String.join(
                System.lineSeparator(),
                orderRepository.findTop10ByOrderByOrderDateDesc().stream()
                    .map(order -> " - " + order.getId() + " | " + order.getOrderNumber())
                    .toList()
            );
            log.info("\nÚltimos 10 pedidos:{}{}", System.lineSeparator(), lastOrders);

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

            String ordersByPeriod = String.join(
                System.lineSeparator(),
                orderRepository.findByOrderDateBetween(
                    java.time.LocalDateTime.now().minusDays(1),
                    java.time.LocalDateTime.now().plusDays(1)
                ).stream()
                    .map(order -> " - " + order.getId() + " | " + order.getOrderNumber())
                    .toList()
            );
            log.info("\nPedidos por período:{}{}", System.lineSeparator(), ordersByPeriod);

            String ordersByStatus = String.join(
                System.lineSeparator(),
                orderRepository.findByStatus(OrdersStatus.PENDING).stream()
                    .map(order -> " - " + order.getId() + " | " + order.getOrderNumber())
                    .toList()
            );
            log.info("\nPedidos por status PENDING:{}{}", System.lineSeparator(), ordersByStatus);

            String ordersByClient = String.join(
                System.lineSeparator(),
                orderRepository.findByClientId(client1.getId()).stream()
                    .map(order -> " - " + order.getId() + " | " + order.getOrderNumber())
                    .toList()
            );
            log.info("\nPedidos por cliente client1:{}{}", System.lineSeparator(), ordersByClient);

            String ordersByTotal = String.join(
                System.lineSeparator(),
                orderRepository.findByTotalGreaterThan(new BigDecimal("10.00")).stream()
                    .map(order -> " - " + order.getId() + " | " + order.getOrderNumber() + " | " + order.getTotal())
                    .toList()
            );
            log.info("\nPedidos com total > 10:{}{}", System.lineSeparator(), ordersByTotal);

            String salesByRestaurant = String.join(
                System.lineSeparator(),
                orderRepository.totalSalesByRestaurant().stream()
                    .map(report -> " - " + report.getRestaurant() + " | " + report.getTotalSales())
                    .toList()
            );
            log.info("\nTotal vendas por restaurante:{}{}", System.lineSeparator(), salesByRestaurant);

            String topProducts = String.join(
                System.lineSeparator(),
                orderRepository.topProducts().stream()
                    .map(report -> " - " + report.getProductName() + " | " + report.getTotalOrders())
                    .toList()
            );
            log.info("\nTop produtos:{}{}", System.lineSeparator(), topProducts);

            String revenueByCategory = String.join(
                System.lineSeparator(),
                orderRepository.revenueByCategory().stream()
                    .map(report -> " - " + report.getCategory() + " | " + report.getTotalRevenue())
                    .toList()
            );
            log.info("\nFaturamento por categoria:{}{}", System.lineSeparator(), revenueByCategory);

            String rankingClients = String.join(
                System.lineSeparator(),
                orderRepository.rankingClients().stream()
                    .map(report -> " - " + report.getClientName() + " | " + report.getTotalOrders())
                    .toList()
            );
            log.info("\nRanking clientes:{}{}", System.lineSeparator(), rankingClients);

            log.info("\nCarregamento de dados concluído.");
            
        };
    }
}
