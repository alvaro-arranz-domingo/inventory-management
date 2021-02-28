package com.aarranz.inventory.core.model;

import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ProductMother;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

  @Test
  public void productShouldHaveName() {
    assertThrows(IllegalArgumentException.class, () -> Product.create(null, new Stock(5), Arrays.asList(ArticleGroupMother.anyGroup()) ));
  }

  @Test
  public void productRequierementsNotNull() {
    assertThrows(IllegalArgumentException.class, () -> Product.create(new ProductId("anyName"), new Stock(5),null ));
  }

  @Test
  public void productShouldHaveAtLeastOneArticleRequirement() {
    assertThrows(IllegalArgumentException.class, () -> Product.create(new ProductId("anyName"), new Stock(5), new ArrayList<>()));
  }

  @Test
  public void productShouldHaveStock() {
    assertThrows(IllegalArgumentException.class, () -> Product.create(new ProductId("anyName"), null, Arrays.asList(ArticleGroupMother.anyGroup()) ));
  }

  @Test
  public void productWithRepeatedArticleId() {
    var articleGroupA = ArticleGroupMother.anyGroupWith("1",  1);
    var articleGroupB = ArticleGroupMother.anyGroupWith("1",  2);

    assertThrows(IllegalArgumentException.class, () -> Product.create(new ProductId("anyName"), new Stock(5), Arrays.asList(articleGroupA, articleGroupB)));
  }
}