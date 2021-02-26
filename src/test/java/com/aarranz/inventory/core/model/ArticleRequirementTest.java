package com.aarranz.inventory.core.model;

import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;
import com.aarranz.inventory.mother.ArticleMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleRequirementTest {

  @Test
  public void groupShouldHaveArticle() {
    assertThrows(IllegalArgumentException.class, () -> new ArticleRequirement(null, 10));
  }

  @Test
  public void groupAmountShouldNotBeZero() {
    var article = ArticleMother.anyArticleWithStock(3);
    assertThrows(IllegalArgumentException.class, () -> new ArticleRequirement(article, 0));
  }

  @Test
  public void groupAmountShouldNotBeNegative() {
    var article = ArticleMother.anyArticleWithStock(3);
    assertThrows(IllegalArgumentException.class, () -> new ArticleRequirement(article, -3));
  }

  @Test
  public void stockOfGroupExact() {
    var article = ArticleMother.anyArticleWithStock(40);
    var group = new ArticleRequirement(article, 10);

    assertEquals(4, group.stockOfGroup());
  }

  @Test
  public void stockOfGroupWithUnitsLeft() {
    var article = ArticleMother.anyArticleWithStock(34);
    var group = new ArticleRequirement(article, 10);

    assertEquals(3, group.stockOfGroup());
  }

  @Test
  public void stockOfGroupNotEnough() {
    var article = ArticleMother.anyArticleWithStock(3);
    var group = new ArticleRequirement(article, 7);

    assertEquals(0, group.stockOfGroup());
  }

  @Test
  public void hasStockFor() {
    var article = ArticleMother.anyArticleWithStock(34);
    var group = new ArticleRequirement(article, 10);

    var hasStock = group.hasStockFor(3);

    assertTrue(hasStock);
  }

  @Test
  public void hasStockForExact() {
    var article = ArticleMother.anyArticleWithStock(30);
    var group = new ArticleRequirement(article, 10);

    var hasStock = group.hasStockFor(3);

    assertTrue(hasStock);
  }

  @Test
  public void doNotHasStockFor() {
    var article = ArticleMother.anyArticleWithStock(24);
    var group = new ArticleRequirement(article, 10);

    var hasStock = group.hasStockFor(3);

    assertFalse(hasStock);
  }

  @Test
  public void removeAmount() {
    var article = ArticleMother.anyArticleWithStock(34);
    var group = new ArticleRequirement(article, 10);

    group.removeArticles(2);

    assertEquals(14, group.article().stock());
    assertEquals(1, group.stockOfGroup());
  }

  @Test
  public void removeTooManyAmount() {
    var article = ArticleMother.anyArticleWithStock(34);
    var group = new ArticleRequirement(article, 10);

    assertThrows(NotEnoughStockForArticleException.class, () -> group.removeArticles(4));
  }
}