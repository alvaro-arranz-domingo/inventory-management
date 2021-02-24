package com.aarranz.inventory.core.model.exceptions;

public class NotEnoughStockForaArticleException extends RuntimeException {

  private final String articleId;
  private final int required;
  private final int stock;

  public NotEnoughStockForaArticleException(String articleId, int required, int stock) {
    this.articleId = articleId;
    this.required = required;
    this.stock = stock;
  }
}
