package com.deliverytech.delivery_api.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.request.OrderDTO;
import com.deliverytech.delivery_api.dto.request.OrderedItemDTO;
import com.deliverytech.delivery_api.dto.response.OrderResponseDTO;
import com.deliverytech.delivery_api.dto.response.OrderSummaryResponseDTO;
import com.deliverytech.delivery_api.enums.OrdersStatus;
import com.deliverytech.delivery_api.exceptions.BusinessException;
import com.deliverytech.delivery_api.model.Client;
import com.deliverytech.delivery_api.model.Order;
import com.deliverytech.delivery_api.model.OrderedItem;
import com.deliverytech.delivery_api.model.Product;
import com.deliverytech.delivery_api.model.Restaurant;
import com.deliverytech.delivery_api.repository.ClientRepository;
import com.deliverytech.delivery_api.repository.OrderRepository;
import com.deliverytech.delivery_api.repository.OrderedItemRepository;
import com.deliverytech.delivery_api.repository.ProductRepository;
import com.deliverytech.delivery_api.repository.RestaurantRepository;
import com.deliverytech.delivery_api.service.api.OrderService;

import com.deliverytech.delivery_api.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ClientRepository clientRepository;
    private RestaurantRepository restaurantRepository;
    private OrderedItemRepository orderedItemRepository;
    private ProductRepository productRepository;
    private final ModelMapper mapper;
    private static final String NOT_FOUND_MESSAGE = "Order not found";

    public OrderServiceImpl(
        OrderRepository orderRepository,
        ClientRepository clientRepository,
        RestaurantRepository restaurantRepository,
        OrderedItemRepository orderedItemRepository,
        ProductRepository productRepository,
        ModelMapper mapper
    ) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderedItemRepository = orderedItemRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderResponseDTO createOrder(OrderDTO dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new BusinessException("Order must have at least one item");
        }

        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(() -> new EntityNotFoundException("Client not found"));

        if (!Boolean.TRUE.equals(client.getActive())) {
            throw new BusinessException("Client is not active");
        }

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        if (!Boolean.TRUE.equals(restaurant.getActive())) {
            throw new BusinessException("Restaurant is not active");
        }

        Order newOrder = new Order();
        newOrder.setClient(client);
        newOrder.setRestaurant(restaurant);
        newOrder.setStatus(OrdersStatus.PENDING);
        newOrder.setDeliveryAddress(dto.getDeliveryAddress());
        newOrder.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        Order savedOrder = orderRepository.save(newOrder);

        BigDecimal subtotal = calculateOrderTotal(dto.getItems());

        for (OrderedItemDTO itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));

            if(!Boolean.TRUE.equals(product.getAvailable())) {
                throw new BusinessException("Product is not available");
            }

            if(!product.getRestaurant().getId().equals(restaurant.getId())) {
                throw new BusinessException("Product does not belong to the restaurant");
            }

            if (itemDto.getQuantity() == null || itemDto.getQuantity() <= 0) {
                throw new BusinessException("Item quantity must be greater than zero");
            }

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));

            OrderedItem item = new OrderedItem();
            item.setOrder(savedOrder);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setItemPrice(product.getPrice());
            item.setTotal(itemTotal);

            orderedItemRepository.save(item);
        }

        BigDecimal deliveryFee = restaurant.getDeliveryFee() == null ? BigDecimal.ZERO : restaurant.getDeliveryFee();

        savedOrder.setDeliveryFee(deliveryFee);
        savedOrder.setTotal(subtotal.add(deliveryFee));

        orderRepository.save(savedOrder);
        return mapper.map(savedOrder, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));
        return mapper.map(order, OrderResponseDTO.class);
    }

    @Override
    public List<OrderSummaryResponseDTO> getOrdersByClient(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
            .map(order -> mapper.map(order, OrderSummaryResponseDTO.class))
            .toList();
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, OrdersStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        if (!isValidTransition(order.getStatus(), status)) {
            throw new BusinessException("Invalid status transition");
        }

        order.setStatus(status);
        return mapper.map(orderRepository.save(order), OrderResponseDTO.class);
    }

    @Override
    public BigDecimal calculateOrderTotal(List<OrderedItemDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new BusinessException("Order must have at least one item");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (OrderedItemDTO item : items) {
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new BusinessException("Item quantity must be greater than zero");
            }

            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
            
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        return total;
    }

    @Override
    public OrderResponseDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        if (order.getStatus() != OrdersStatus.PENDING) {
            throw new BusinessException("Order cannot be canceled");
        }

        order.setStatus(OrdersStatus.CANCELED);

        return mapper.map(orderRepository.save(order), OrderResponseDTO.class);
    }

    @Override
    public List<OrderResponseDTO> listOrders(OrdersStatus status, String startDate, String endDate) {
        List<Order> orders;
        
        if (status != null && startDate != null && endDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            orders = orderRepository.findReportByPeriodAndStatus(status, start, end);
        } else if (status != null) {
            orders = orderRepository.findByStatus(status);
        } else if (startDate != null && endDate != null) {
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            orders = orderRepository.findByOrderDateBetween(start, end);
        } else {
            orders = orderRepository.findAll();
        }
        
        return orders.stream()
            .map(o -> mapper.map(o, OrderResponseDTO.class))
            .toList();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByRestaurant(Long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        return orders.stream()
            .map(o -> mapper.map(o, OrderResponseDTO.class))
            .toList();
    }

    @Override
    public List<OrderResponseDTO> getMyOrders() {
        com.deliverytech.delivery_api.model.User currentUser = com.deliverytech.delivery_api.security.SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            throw new BusinessException("No authenticated user found");
        }
        
        List<Order> orders = orderRepository.findAll().stream()
            .filter(order -> order.getClient().getEmail().equals(currentUser.getEmail()))
            .toList();
        
        return orders.stream()
            .map(o -> mapper.map(o, OrderResponseDTO.class))
            .toList();
    }

    @Override
    public List<OrderResponseDTO> getMyRestaurantOrders() {
        com.deliverytech.delivery_api.model.User currentUser = com.deliverytech.delivery_api.security.SecurityUtils.getCurrentUser();
        if (currentUser == null || currentUser.getRestaurantId() == null) {
            throw new BusinessException("No authenticated restaurant user found");
        }
        
        return getOrdersByRestaurant(currentUser.getRestaurantId());
    }

    private boolean isValidTransition(OrdersStatus current, OrdersStatus next) {
        if (current == OrdersStatus.PENDING) {
            return next == OrdersStatus.CONFIRMED || next == OrdersStatus.CANCELED;
        }
        if (current == OrdersStatus.CONFIRMED) {
            return next == OrdersStatus.DELIVERED;
        }
        return false;
    }

    @Override
    public boolean canAccess(Long orderId) {
        com.deliverytech.delivery_api.model.User currentUser = com.deliverytech.delivery_api.security.SecurityUtils.getCurrentUser();
        if (currentUser == null) {
            return false;
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_MESSAGE));

        
        if (currentUser.getRole() == com.deliverytech.delivery_api.enums.UserRole.ADMIN) {
            return true;
        }

        
        if (currentUser.getRole() == com.deliverytech.delivery_api.enums.UserRole.CLIENT) {
            
            
            return true;
        }

        
        if (currentUser.getRole() == com.deliverytech.delivery_api.enums.UserRole.RESTAURANT && currentUser.getRestaurantId() != null) {
            return order.getRestaurant().getId().equals(currentUser.getRestaurantId());
        }

        return false;
    }
}
