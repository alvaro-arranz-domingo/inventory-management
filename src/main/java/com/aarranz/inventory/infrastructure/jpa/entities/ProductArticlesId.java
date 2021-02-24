package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ProductArticlesId implements Serializable {

  @ManyToOne(fetch = FetchType.EAGER)
  public ArticleEntity article;

  @ManyToOne(fetch = FetchType.EAGER)
  public ProductEntity product;
}
