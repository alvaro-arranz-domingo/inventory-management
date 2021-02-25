package com.aarranz.inventory.infrastructure.jpa.mappers;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleRequirement;
import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.infrastructure.jpa.entities.ArticleEntity;
import com.aarranz.inventory.infrastructure.jpa.entities.ProductArticlesEntity;
import com.aarranz.inventory.infrastructure.jpa.entities.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductEntityMapper {

  public ProductEntity toEntity(Product product) {
    var entity = new ProductEntity();
    entity.name = product.name();
    entity.productArticles = product.articlesRequired().stream().map(a -> toEntity(entity, a)).collect(Collectors.toSet());
    return entity;
  }

  public Product toDom(ProductEntity entity) {
    var requirements = entity.productArticles.stream().map(this::toDom).collect(Collectors.toSet());
    return new Product(entity.name, requirements);
  }

  private ProductArticlesEntity toEntity(ProductEntity productEntity, ArticleRequirement requirement) {
    var entity = new ProductArticlesEntity();
    entity.article = toEntity(requirement.article());
    entity.product = productEntity;
    entity.amount = requirement.amount();
    return entity;
  }

  private ArticleRequirement toDom(ProductArticlesEntity entity) {
    return new ArticleRequirement(toDom(entity.article), entity.amount);
  }

  private ArticleEntity toEntity(Article article) {
    var entity = new ArticleEntity();
    entity.id = article.id();
    entity.name = article.name();
    entity.stock = article.stock();
    return entity;
  }

  private Article toDom(ArticleEntity entity) {
    return new Article(entity.id, entity.name, entity.stock);
  }
}
