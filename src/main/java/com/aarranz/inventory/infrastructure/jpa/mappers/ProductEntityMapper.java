package com.aarranz.inventory.infrastructure.jpa.mappers;

import com.aarranz.inventory.core.model.*;
import com.aarranz.inventory.infrastructure.jpa.entities.ArticleEntity;
import com.aarranz.inventory.infrastructure.jpa.entities.ProductArticlesEntity;
import com.aarranz.inventory.infrastructure.jpa.entities.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ProductEntityMapper {

  public ProductEntity toEntity(Product product) {
    var entity = new ProductEntity();
    entity.name = product.name().value();
    entity.stock = product.stock().value();
    entity.productArticles = StreamSupport.stream(product.requiredArticles().spliterator(), false).map(a -> toEntity(entity, a)).collect(Collectors.toSet());
    return entity;
  }

  public Product toDom(ProductEntity entity) {
    var requirements = entity.productArticles.stream().map(this::toDom).collect(Collectors.toSet());
    return new Product(new ProductId(entity.name), new Stock(entity.stock), requirements);
  }

  private ProductArticlesEntity toEntity(ProductEntity productEntity, ArticleRequirement requirement) {
    var entity = new ProductArticlesEntity();
    entity.id.productId = productEntity.name;
    entity.id.articleId = requirement.articleId().value();
    entity.product = productEntity;
    entity.amount = requirement.amount();
    return entity;
  }

  private ArticleRequirement toDom(ProductArticlesEntity entity) {
    return new ArticleRequirement(new ArticleId(entity.id.articleId), entity.amount);
  }
}
