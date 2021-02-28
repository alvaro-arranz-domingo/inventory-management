package com.aarranz.inventory.core.model;

import java.util.Objects;

public class Stock {

  private int amount;

  public Stock(int amount) {
    checkAmountIsValid(amount);
    this.amount = amount;
  }

  public int value() {
    return amount;
  }

  public boolean hasStockFor(int quantity) {
    return amount >= quantity;
  }

  public void removeAmount(int quantity) {

    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity to remove in a Stock should be positive");
    }

    if (!hasStockFor(quantity)) {
      throw new IllegalArgumentException();
    }

    amount -= quantity;
  }

  private void checkAmountIsValid(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("The amount in a Stock should not be negative");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Stock stock = (Stock) o;
    return amount == stock.amount;
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount);
  }
}
