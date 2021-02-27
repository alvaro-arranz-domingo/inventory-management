package com.aarranz.inventory.core.model;

import java.util.Objects;

public class ProductId {

  private final String id;

  public ProductId(String id) {
    checkIsNotNull(id);
    this.id = id;
  }

  private void checkIsNotNull(String id) {
    if (id == null) {
      throw new IllegalArgumentException();
    }
  }

  public String value() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductId productId = (ProductId) o;
    return Objects.equals(id, productId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
