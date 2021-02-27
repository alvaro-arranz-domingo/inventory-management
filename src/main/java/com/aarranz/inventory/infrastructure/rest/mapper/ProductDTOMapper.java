package com.aarranz.inventory.infrastructure.rest.mapper;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.infrastructure.rest.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper {

  public ProductDTO toDTO(Product product) {
    var dto = new ProductDTO();
    dto.name = product.name().value();
    dto.stock = product.stock();
    return dto;
  }
}
