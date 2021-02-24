package com.aarranz.inventory.mother;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleRequirement;

public class ArticleGroupMother {

  public static ArticleRequirement anyGroup() {
    var article = Article.create("anyId", "anyName", 3);
    return new ArticleRequirement(article, 2);
  }

  public static ArticleRequirement anyGroupWith(String articleId, int stock, int amount) {
    var article = Article.create(articleId, "anyName", stock);
    return new ArticleRequirement(article, amount);
  }
}
