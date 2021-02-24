package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "ProductArticles")
@AssociationOverrides({
    @AssociationOverride(name = "id.article",
        joinColumns = @JoinColumn(name = "idArticle")),
    @AssociationOverride(name = "id.product",
        joinColumns = @JoinColumn(name = "idProduct")) })
public class ProductArticlesEntity {

  @EmbeddedId
  public ProductArticlesId id;

  public int amount;

}
