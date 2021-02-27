package com.aarranz.inventory.core.model;

import java.util.Objects;

public class ArticleId {

  private final String id;

  public ArticleId(String id) {
    checkIsNotNull(id);
    this.id = id;
  }

  private void checkIsNotNull(String id) {
    if (id == null) {
      throw new IllegalArgumentException();
    }
  }

  public String value() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleId articleId = (ArticleId) o;
    return Objects.equals(id, articleId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
