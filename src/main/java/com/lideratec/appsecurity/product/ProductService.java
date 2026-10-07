package com.lideratec.appsecurity.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Lógica para listar todos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //buscar producto por ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto con ID " + id + " no existe"));
    }

    // Lógica para CREAR
    public Product createProduct(Product request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());

        // Guarda el producto en la tabla "products" de PostgreSQL
        return productRepository.save(product);
    }

    //actualizar productoo
    public Product updateProduct(Long id, Product request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto con ID " + id + " no existe"));

        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());

        return productRepository.save(existingProduct);
    }

    // Lógica para ELIMINAR
    public void deleteProduct(Long id) {
        // Verificamos si existe antes de borrar
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("El producto con ID " + id + " no existe");
        }
        productRepository.deleteById(id);
    }
}