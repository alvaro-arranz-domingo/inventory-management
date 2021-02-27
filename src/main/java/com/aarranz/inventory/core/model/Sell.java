package com.aarranz.inventory.core.model;

public class Sell {

  private final Long id;
  private final ProductId productId;
  private final int amount;

  public Sell(Long id, ProductId productId, int amount) {
    this.id = id;
    this.productId = productId;
    this.amount = amount;
  }

  static public Sell create(ProductId productId, int amount) {

    checkProductIdNotNull(productId);
    checkAmountIsValid(amount);

    return new Sell(null, productId, amount);
  }

  public Long id() {
    return id;
  }

  public ProductId productId() {
    return productId;
  }

  public int amount() {
    return amount;
  }

  private static void checkAmountIsValid(long amount) {
    if (amount < 1) {
      throw new IllegalArgumentException();
    }
  }

  private static void checkProductIdNotNull(ProductId productId) {
    if (productId == null) {
      throw new IllegalArgumentException();
    }
  }
}
