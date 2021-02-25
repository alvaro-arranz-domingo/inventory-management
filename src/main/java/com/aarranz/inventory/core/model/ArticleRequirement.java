package com.aarranz.inventory.core.model;

import java.util.Objects;

public class ArticleRequirement {

  private final Article article;
  private final int amount;

  public ArticleRequirement(Article article, int amount) {
    checkArticleNotNull(article);
    checkAmount(amount);

    this.article = article;
    this.amount = amount;
  }

  public Article article() {
    return article;
  }

  public int amount() {
    return amount;
  }

  public int stockOfGroup() {
    return Math.floorDiv(article.stock(), amount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleRequirement that = (ArticleRequirement) o;
    return Objects.equals(article, that.article);
  }

  @Override
  public int hashCode() {
    return Objects.hash(article);
  }

  private void checkAmount(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount should be a positive value");
    }
  }

  private void checkArticleNotNull(Article article) {
    if (article == null) {
      throw new IllegalArgumentException("Article should not be null");
    }
  }
}
