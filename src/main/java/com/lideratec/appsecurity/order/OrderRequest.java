package com.lideratec.appsecurity.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequest {

    @NotBlank
    @Size(max = 200)
    private String description;
}