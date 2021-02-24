package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "ProductArticles")
public class ProductArticlesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long id;

  public int amount;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "idArticle")
  public ArticleEntity article;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "idProduct")
  public ProductEntity product;

}
