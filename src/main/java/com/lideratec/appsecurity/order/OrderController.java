package com.lideratec.appsecurity.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request, Authentication authentication) {
        // authentication.getName() extrae el username del token validado por el filtro JWT
        String username = authentication.getName();
        Order createdOrder = orderService.createOrder(request, username);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        List<Order> orders = orderService.getMyOrders(username);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();

        // Verificamos si el usuario actual tiene rol ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        Order order = orderService.getOrderById(id, username, isAdmin);
        return ResponseEntity.ok(order);
    }
}