package com.aarranz.inventory.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleRequirementTest {

  @Test
  public void articleIdShouldNotBeNull() {
    assertThrows(IllegalArgumentException.class, () -> new ArticleRequirement(null, 10));
  }

  @Test
  public void amountShouldNotBeZero() {
    assertThrows(IllegalArgumentException.class, () -> new ArticleRequirement(new ArticleId("1"), 0));
  }

  @Test
  public void amountShouldNotBeNegative() {
    assertThrows(IllegalArgumentException.class, () -> new ArticleRequirement(new ArticleId("1"), -3));
  }
}