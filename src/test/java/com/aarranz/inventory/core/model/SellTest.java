package com.aarranz.inventory.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellTest {

  @Test
  public void createSell() {
    Sell.create("productId", 6);
  }

  @Test
  public void sellWithNullProductId() {
    assertThrows(IllegalArgumentException.class, () -> Sell.create(null, 5));
  }

  @Test
  public void sellWithZeroAmount() {
    assertThrows(IllegalArgumentException.class, () -> Sell.create("productId", 0));
  }

  @Test
  public void sellWithNegativeAmount() {
    assertThrows(IllegalArgumentException.class, () -> Sell.create("productId", -1));
  }
}