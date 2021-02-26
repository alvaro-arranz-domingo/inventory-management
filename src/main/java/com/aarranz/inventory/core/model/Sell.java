package com.aarranz.inventory.core.model;

public class Sell {

  private final Long id;
  private final String productId;
  private final int amount;

  public Sell(Long id, String productId, int amount) {
    this.id = id;
    this.productId = productId;
    this.amount = amount;
  }

  static public Sell create(String productId, int amount) {

    checkProductIdNotNull(productId);
    checkAmountIsValid(amount);

    return new Sell(null, productId, amount);
  }

  public Long id() {
    return id;
  }

  public String productId() {
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

  private static void checkProductIdNotNull(String productId) {
    if (productId == null) {
      throw new IllegalArgumentException();
    }
  }
}
