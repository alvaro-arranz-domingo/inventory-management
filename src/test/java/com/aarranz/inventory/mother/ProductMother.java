package com.aarranz.inventory.mother;

import com.aarranz.inventory.core.model.ArticleRequirement;
import com.aarranz.inventory.core.model.Product;

import java.util.Arrays;

public class ProductMother {

  public static Product anyProductWithArticles(ArticleRequirement... articles) {
    return Product.create("anyName", Arrays.asList(articles));
  }
}
