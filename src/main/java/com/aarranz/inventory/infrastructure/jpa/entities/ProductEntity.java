package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Products")
public class ProductEntity {

  @Id
  public String name;

  public int stock;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<ProductArticlesEntity> productArticles;
}
