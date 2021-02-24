package com.aarranz.inventory.mother;

import com.aarranz.inventory.core.model.Article;

public class ArticleMother {

  public static Article anyArticleWithStock(int stock) {
    return Article.create("anyId", "anyName", stock);
  }

}
