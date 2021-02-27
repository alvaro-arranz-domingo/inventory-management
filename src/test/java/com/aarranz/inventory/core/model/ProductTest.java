package com.aarranz.inventory.core.model;

import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;
import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ProductMother;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

  @Test
  public void productShouldHaveName() {
    var articleGroup = ArticleGroupMother.anyGroup();
    assertThrows(IllegalArgumentException.class, () -> Product.create(null, Arrays.asList(articleGroup) ));
  }

  @Test
  public void productRequierementsNotNull() {
    assertThrows(IllegalArgumentException.class, () -> Product.create(new ProductId("anyName"), null ));
  }

  @Test
  public void productShouldHaveAtLeastOneArticleRequirement() {
    assertThrows(IllegalArgumentException.class, () -> Product.create(new ProductId("anyName"), new ArrayList<>()));
  }

  @Test
  public void productWithRepeatedArticleId() {
    var articleGroupA = ArticleGroupMother.anyGroupWith("1", 3, 1);
    var articleGroupB = ArticleGroupMother.anyGroupWith("1", 5, 2);

    assertThrows(IllegalArgumentException.class, () -> Product.create(new ProductId("anyName"), Arrays.asList(articleGroupA, articleGroupB)));
  }

  @Test
  public void stockOneArticle() {
    var article = ArticleGroupMother.anyGroupWith("1", 7, 2);
    var product = ProductMother.anyProductWithArticles(article);

    var stock = product.stock();

    assertEquals(3, stock);
  }

  @Test
  public void stockOneArticleNoStock() {
    var article = ArticleGroupMother.anyGroupWith("1", 2, 5);
    var product = ProductMother.anyProductWithArticles(article);

    var stock = product.stock();

    assertEquals(0, stock);
  }

  @Test
  public void stockSeveralArticles() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 7, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 6, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);
    var product = ProductMother.anyProductWithArticles(article1, article2, article3);

    var stock = product.stock();

    assertEquals(2, stock);
  }

  @Test
  public void stockSeveralArticlesNoStock() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 7, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 2, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);
    var product = ProductMother.anyProductWithArticles(article1, article2, article3);

    var stock = product.stock();

    assertEquals(0, stock);
  }

  @Test
  public void hasStockFor() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 7, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 12, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);
    var product = ProductMother.anyProductWithArticles(article1, article2, article3);

    var hasStock = product.hasStockFor(3);

    assertTrue(hasStock);
  }

  @Test
  public void doNotHaveStockFor() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 5, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 12, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);
    var product = ProductMother.anyProductWithArticles(article1, article2, article3);

    var hasStock = product.hasStockFor(3);

    assertFalse(hasStock);
  }

  @Test
  public void removeProduct() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 7, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 12, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);
    var product = ProductMother.anyProductWithArticles(article1, article2, article3);

    product.removeAmount(2);

    assertEquals(3, article1.article().stock());
    assertEquals(6, article2.article().stock());
    assertEquals(6, article3.article().stock());
    assertEquals(1, product.stock());
  }

  @Test
  public void removeTooManyAmount() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 7, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 12, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);
    var product = ProductMother.anyProductWithArticles(article1, article2, article3);

    assertThrows(NotEnoughStockForArticleException.class, ()-> product.removeAmount(5));
  }
}