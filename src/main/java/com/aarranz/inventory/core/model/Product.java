package com.aarranz.inventory.core.model;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Product {

  private final ProductId name;
  private final Stock stock;
  private final Set<ArticleRequirement> articles;

  public Product(ProductId name, Stock stock, Set<ArticleRequirement> articles) {
    this.name = name;
    this.stock = stock;
    this.articles = articles;
  }

  public static Product create(ProductId name, Stock stock, List<ArticleRequirement> articles) {
    checkNotNull(name);
    checkNotNull(stock);
    checkArticles(articles);

    return new Product(name, stock, new HashSet<>(articles));
  }

  public ProductId name() {
    return name;
  }

  public Stock stock() {
    return stock;
  }

  public Iterable<ArticleRequirement> requiredArticles() {
    return articles;
  }

  public Optional<ArticleRequirement> getRequiredArticleById(ArticleId articleId) {
    return articles.stream().filter(a -> a.articleId().equals(articleId)).findFirst();
  }

  private static void checkNotNull(Object object) {
    if (object == null) {
      throw new IllegalArgumentException("The product should have a name different from null");
    }
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
}
