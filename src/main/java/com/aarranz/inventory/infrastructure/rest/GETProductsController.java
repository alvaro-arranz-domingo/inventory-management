package com.aarranz.inventory.infrastructure.rest;

import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.usecases.GetAllProductsUC;
import com.aarranz.inventory.infrastructure.rest.dto.ProductDTO;
import com.aarranz.inventory.infrastructure.rest.mapper.ProductDTOMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GETProductsController {

  private final GetAllProductsUC useCase;
  private final ProductDTOMapper mapper;

  public GETProductsController(GetAllProductsUC useCase, ProductDTOMapper mapper) {
    this.useCase = useCase;
    this.mapper = mapper;
  }

  @GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ProductDTO> geAllProducts() {

    return useCase
        .get().stream()
        .map(mapper::toDTO)
        .collect(Collectors.toList());
  }
}
