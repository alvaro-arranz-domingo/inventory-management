package com.aarranz.inventory.mother;

import com.aarranz.inventory.core.model.ArticleRequirement;
import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.model.Stock;

import java.util.Arrays;

public class ProductMother {

  public static Product anyProductWithArticles(ArticleRequirement... articles) {
    return Product.create(new ProductId("anyName"), new Stock(5), Arrays.asList(articles));
  }

  public static Product anyProductWithArticles(String name, ArticleRequirement... articles) {
    return Product.create(new ProductId(name), new Stock(5), Arrays.asList(articles));
  }

  public static Product anyProductWithArticles(String name, Stock stock, ArticleRequirement... articles) {
    return Product.create(new ProductId(name), stock, Arrays.asList(articles));
  }

}
