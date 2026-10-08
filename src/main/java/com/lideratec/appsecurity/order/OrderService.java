package com.lideratec.appsecurity.order;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(OrderRequest request, String username) {
        Order order = Order.builder()
                .description(request.getDescription())
                .username(username) // Asignamos automáticamente el usuario autenticado
                .build();
        return orderRepository.save(order);
    }

    public List<Order> getMyOrders(String username) {
        return orderRepository.findByUsername(username);
    }

    public Order getOrderById(Long id, String username, boolean isAdmin) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Si el usuario no es el dueño Y tampoco es ADMIN, se le deniega el acceso.
        if (!order.getUsername().equals(username) && !isAdmin) {
            throw new AccessDeniedException("No tienes permiso para ver esta orden.");
        }

        return order;
    }
}