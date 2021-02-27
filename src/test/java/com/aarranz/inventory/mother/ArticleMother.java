package com.aarranz.inventory.mother;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleId;

public class ArticleMother {

  public static Article anyArticleWithStock(int stock) {
    return Article.create(new ArticleId("anyId"), "anyName", stock);
  }

}
