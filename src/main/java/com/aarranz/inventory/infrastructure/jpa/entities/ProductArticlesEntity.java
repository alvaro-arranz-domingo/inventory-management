package com.aarranz.inventory.infrastructure.jpa.entities;

import com.aarranz.inventory.core.model.Article;

import javax.persistence.*;

@Entity
@Table(name = "ProductArticles")
public class ProductArticlesEntity {

  @EmbeddedId
  public ProductArticlesId id = new ProductArticlesId();

  public int amount;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @MapsId("productId")
  public ProductEntity product;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @MapsId("articleId")
  public ArticleEntity article;

}
