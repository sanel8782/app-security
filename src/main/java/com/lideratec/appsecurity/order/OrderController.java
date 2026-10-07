package com.lideratec.appsecurity.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public ResponseEntity<String> getOrders() {
        return ResponseEntity.ok("¡Acceso exitoso a las órdenes!");
    }
}
