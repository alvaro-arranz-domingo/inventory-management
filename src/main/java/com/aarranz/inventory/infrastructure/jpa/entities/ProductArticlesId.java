package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductArticlesId implements Serializable {

  public String articleId;
  public String productId;
}
