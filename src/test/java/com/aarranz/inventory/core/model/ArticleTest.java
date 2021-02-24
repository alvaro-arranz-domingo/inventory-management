package com.aarranz.inventory.core.model;

import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForaArticleException;
import com.aarranz.inventory.mother.ArticleMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

  @Test
  public void articleStockShouldNotBeNegative() {
    assertThrows(IllegalArgumentException.class, () -> ArticleMother.anyArticleWithStock(-1));
  }

  @Test
  public void articleShouldHaveId() {
    assertThrows(IllegalArgumentException.class, () -> Article.create(null, "anyName", 1));
  }

  @Test
  public void articleShouldHaveName() {
    assertThrows(IllegalArgumentException.class, () -> Article.create("anyId", null, 1));
  }

  @Test
  public void articleReduceStock() {
    var article = ArticleMother.anyArticleWithStock(8);

    article.reduceStockIn(3);

    assertEquals(5, article.stock());
  }

  @Test
  public void articleReduceStockZero() {
    var article = ArticleMother.anyArticleWithStock(5);

    article.reduceStockIn(0);

    assertEquals(5, article.stock());
  }

  @Test
  public void articleReduceStockNegativeValue() {
    var article = ArticleMother.anyArticleWithStock(8);

    assertThrows(IllegalArgumentException.class, () -> article.reduceStockIn(-1));
  }

  @Test
  public void articleReduceStockNotEnough() {
    var article = ArticleMother.anyArticleWithStock(4);

    assertThrows(NotEnoughStockForaArticleException.class, () -> article.reduceStockIn(7));
  }

}