package com.aarranz.inventory.core.model;

import java.util.Objects;

public class ArticleRequirement {

  private final ArticleId articleId;
  private final int amount;

  public ArticleRequirement(ArticleId articleId, int amount) {
    checkArticleNotNull(articleId);
    checkAmountIsValid(amount);

    this.articleId = articleId;
    this.amount = amount;
  }

  public ArticleId articleId() {
    return articleId;
  }

  public int amount() {
    return amount;
  }

  private void checkAmountIsValid(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount should be a positive value");
    }
  }

  private void checkArticleNotNull(ArticleId id) {
    if (id == null) {
      throw new IllegalArgumentException("ArticleId should not be null");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleRequirement that = (ArticleRequirement) o;
    return Objects.equals(articleId, that.articleId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleId);
  }
}
