package com.lideratec.appsecurity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Método clave para obtener solo las órdenes del usuario logueado
    List<Order> findByUsername(String username);
}