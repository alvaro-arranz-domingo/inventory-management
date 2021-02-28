package com.aarranz.inventory.infrastructure.file;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.infrastructure.file.dto.InventoryDTO;
import com.aarranz.inventory.infrastructure.file.dto.ProductsDTO;
import com.aarranz.inventory.infrastructure.file.mapper.ArticleFileDTOMapper;
import com.aarranz.inventory.infrastructure.file.mapper.ProductFileDTOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoadInventoryFromFile {

  private final ObjectMapper objectMapper;
  private final ProductFileDTOMapper productFileDTOMapper;
  private final ArticleFileDTOMapper articleFileDTOMapper;

  public LoadInventoryFromFile(ObjectMapper objectMapper,
                               ProductFileDTOMapper productFileDTOMapper,
                               ArticleFileDTOMapper articleFileDTOMapper) {
    this.objectMapper = objectMapper;
    this.productFileDTOMapper = productFileDTOMapper;
    this.articleFileDTOMapper = articleFileDTOMapper;
  }

  public List<Article> loadArticles(Resource articlesResource) throws IOException {
    var articles = objectMapper.readValue(articlesResource.getFile(), InventoryDTO.class);

    return articles.inventory.stream()
        .map(articleFileDTOMapper::toDom)
        .collect(Collectors.toList());
  }

  public List<Product> loadProducts(Resource productsResource) throws IOException {
    var products = objectMapper.readValue(productsResource.getFile(), ProductsDTO.class);

    return products.products.stream()
        .map(productFileDTOMapper::toDom)
        .collect(Collectors.toList());
  }
}
