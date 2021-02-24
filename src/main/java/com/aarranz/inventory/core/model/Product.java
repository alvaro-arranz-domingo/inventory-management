package com.aarranz.inventory.core.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Product {

  private final String name;
  private final Set<ArticleRequirement> articles;

  public Product(String name, Set<ArticleRequirement> articles) {
    this.name = name;
    this.articles = articles;
  }

  public static Product create(String name, List<ArticleRequirement> articles) {
    checkNameNotNull(name);
    checkArticles(articles);

    return new Product(name, new HashSet<>(articles));
  }

  public int stock() {
    var optMin = articles.stream()
        .map(ArticleRequirement::stockOfGroup)
        .mapToInt(i -> i)
        .min();

    if (optMin.isEmpty())
      return 0;

    return optMin.getAsInt();
  }

  private static void checkArticles(List<ArticleRequirement> articles) {
    if (articles == null
        || articles.size() == 0) {
      throw new IllegalArgumentException("The product should have at least one article required");
    }

    if (articles.stream().distinct().count() != articles.size()) {
      throw new IllegalArgumentException("The product cannot have a repeated article requirement");
    }
  }

  private static void checkNameNotNull(String name) {
    if (name == null) {
      throw new IllegalArgumentException("The product should have a name different from null");
    }
  }
}
