package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Products")
public class ProductEntity {

  @Id
  public String name;

  @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
  public Set<ProductArticlesEntity> productArticles;
}
