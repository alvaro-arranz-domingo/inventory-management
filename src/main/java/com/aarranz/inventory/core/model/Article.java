package com.aarranz.inventory.core.model;

import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;

import java.util.Objects;

public class Article {

  private final ArticleId id;
  private final String name;
  private int stock;

  public Article(ArticleId id, String name, int stock) {
    this.id = id;
    this.name = name;
    this.stock = stock;
  }

  public static Article create(ArticleId id, String name, int stock) {
    checkStock(stock);
    checkIdNotNull(id);
    checkNameNotNull(name);

    return new Article(id, name, stock);
  }

  public ArticleId id() {
    return id;
  }

  public String name() {
    return name;
  }

  public int stock() {
    return stock;
  }

  public void reduceStockIn(int quantity) {
    checkQuantity(quantity);
    checkEnoughStockFor(quantity);

    stock -= quantity;
  }

  private boolean hasEnoughStockFor(int requirement) {
    return stock >= requirement;
  }

  private static void checkIdNotNull(ArticleId id) {
    if (id == null) {
      throw new IllegalArgumentException("Id should not be null");
    }
  }

  private static void checkStock(int stock) {
    if (stock < 0) {
      throw new IllegalArgumentException("Stock should be positive value or zero");
    }
  }

  private void checkQuantity(int quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity value should not be negative");
    }
  }

  private void checkEnoughStockFor(int quantity) {
    if (!hasEnoughStockFor(quantity)) {
      throw new NotEnoughStockForArticleException();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Article article = (Article) o;
    return Objects.equals(id, article.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  private static void checkNameNotNull(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name should not be null");
    }
  }

}
