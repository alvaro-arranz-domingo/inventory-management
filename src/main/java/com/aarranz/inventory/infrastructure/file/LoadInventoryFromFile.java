package com.aarranz.inventory.infrastructure.file;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.infrastructure.file.dto.InventoryDTO;
import com.aarranz.inventory.infrastructure.file.dto.ProductsDTO;
import com.aarranz.inventory.infrastructure.file.mapper.ProductFileDTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LoadInventoryFromFile {

  private final ObjectMapper objectMapper;
  private final ProductFileDTOMapper productFileDTOMapper;

  public LoadInventoryFromFile(ObjectMapper objectMapper,
                               ProductFileDTOMapper productFileDTOMapper) {
    this.objectMapper = objectMapper;
    this.productFileDTOMapper = productFileDTOMapper;
  }

  public List<Product> load(Resource articlesResource, Resource productsResource) throws IOException {

    var articles = objectMapper.readValue(articlesResource.getFile(), InventoryDTO.class);
    var products = objectMapper.readValue(productsResource.getFile(), ProductsDTO.class);

    return productFileDTOMapper.toDom(products.products, articles.inventory);
  }
}
