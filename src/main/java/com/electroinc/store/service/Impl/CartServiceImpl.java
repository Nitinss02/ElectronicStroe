package com.electroinc.store.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electroinc.store.Dto.AddItemtoCartRequest;
import com.electroinc.store.Dto.CartDto;
import com.electroinc.store.Entity.Cart;
import com.electroinc.store.Entity.CartItem;
import com.electroinc.store.Entity.Product;
import com.electroinc.store.Entity.User;
import com.electroinc.store.Exception.BadApiRequest;
import com.electroinc.store.Exception.ResourceNotFound;
import com.electroinc.store.Repository.CartItemRepository;
import com.electroinc.store.Repository.CartRepository;
import com.electroinc.store.Repository.ProductRepository;
import com.electroinc.store.Repository.UserRepository;
import com.electroinc.store.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto AddItemtoCart(String userId, AddItemtoCartRequest addItemtoCartRequest) {
        String productId = addItemtoCartRequest.getProductId();
        int productQuantity = addItemtoCartRequest.getProductQuantity();

        if (productQuantity <= 0) {
            throw new BadApiRequest("Quantity is Invalid");
        }
        // fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product is not found"));
        // fetch the User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User is not found by given Id"));
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException ex) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> items = cart.getItem();

        items = items.stream().map((item) -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(productQuantity);
                item.setTotalPrice(productQuantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        if (!updated.get()) {
            CartItem cartItems = CartItem.builder()
                    .quantity(productQuantity)
                    .totalPrice(productQuantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItem().add(cartItems);
        }

        cart.setUser(user);
        Cart savedCart = cartRepository.save(cart);

        return mapper.map(savedCart, CartDto.class);
    }

    @Override
    public void RemoveItemtoCart(String userId, int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFound("Item is not found "));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void ClearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User is not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFound("Cart is not found"));
        cart.getItem().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User is not found"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFound("Cart is not found"));

        return mapper.map(cart, CartDto.class);
    }

}
