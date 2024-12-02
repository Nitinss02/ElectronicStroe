package com.electroinc.store.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electroinc.store.Dto.CreateOrderDto;
import com.electroinc.store.Dto.OrderDto;
import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Entity.Cart;
import com.electroinc.store.Entity.CartItem;
import com.electroinc.store.Entity.Order;
import com.electroinc.store.Entity.OrderItem;
import com.electroinc.store.Entity.User;
import com.electroinc.store.Exception.BadApiRequest;
import com.electroinc.store.Exception.ResourceNotFound;
import com.electroinc.store.Helper.helper;
import com.electroinc.store.Repository.CartRepository;
import com.electroinc.store.Repository.OrderRepository;
import com.electroinc.store.Repository.UserRepository;
import com.electroinc.store.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDto CreateOrder(CreateOrderDto orderDto) {

        String userId = orderDto.getUserId();
        String cartId = orderDto.getOrderId();
        // fetch the user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User id is Invalid"));
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFound("cart id is Invalid"));
        List<CartItem> cartItem = cart.getItem();
        if (cartItem.size() <= 0) {
            throw new BadApiRequest("Invalid number of Item");
        }
        Order order = Order.builder().billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmmount = new AtomicReference<>(0);

        List<OrderItem> orders = cartItem.stream().map(CartItm -> {

            OrderItem orderItem = OrderItem.builder()
                    .quantity(CartItm.getQuantity())
                    .product(CartItm.getProduct())
                    .totalPrice(CartItm.getQuantity() * CartItm.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            orderAmmount.set(orderAmmount.get() + orderItem.getTotalPrice());

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orders);
        order.setOrderAmmount(orderAmmount.get());

        cart.getItem().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void RemoveOrder(String OrderId) {
        Order order = orderRepository.findById(OrderId)
                .orElseThrow(() -> new ResourceNotFound("Order Id is not Found"));
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDto> GetUserOrder(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User is not found"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDto = orders.stream().map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
        return orderDto;

    }

    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Order> orderPage = orderRepository.findAll(pageable);

        return helper.getPageableResponse(orderPage, OrderDto.class);

    }

}
