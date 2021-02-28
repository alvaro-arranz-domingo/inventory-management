package com.aarranz.inventory.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockTest {

  @Test
  public void validStock() {
    var stock = new Stock(5);

    assertEquals(5, stock.value());
  }

  @Test
  public void stockShouldNotBeNegative() {
    assertThrows(IllegalArgumentException.class, () -> new Stock(-2));
  }

  @Test
  public void removeValidAmount() {
    var stock = new Stock(5);

    stock.removeAmount(2);

    assertEquals(3, stock.value());
  }

  @Test
  public void removeExactAmount() {
    var stock = new Stock(5);

    stock.removeAmount(5);

    assertEquals(0, stock.value());
  }

  @Test
  public void removeInvalidAmount() {
    var stock = new Stock(5);

    assertThrows(IllegalArgumentException.class, () -> stock.removeAmount(7));
  }

  @Test
  public void removeNegativeAmount() {
    var stock = new Stock(5);

    assertThrows(IllegalArgumentException.class, () -> stock.removeAmount(-7));
  }

  @Test
  public void removeZeroAmount() {
    var stock = new Stock(5);

    assertThrows(IllegalArgumentException.class, () -> stock.removeAmount(0));
  }
}
