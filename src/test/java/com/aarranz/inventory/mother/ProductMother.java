package com.aarranz.inventory.mother;

import com.aarranz.inventory.core.model.ArticleRequirement;
import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.ProductId;

import java.util.Arrays;

public class ProductMother {

  public static Product anyProductWithArticles(ArticleRequirement... articles) {
    return Product.create(new ProductId("anyName"), Arrays.asList(articles));
  }

  public static Product anyProductWithArticles(String name, ArticleRequirement... articles) {
    return Product.create(new ProductId(name), Arrays.asList(articles));
  }

}
