package com.aarranz.inventory.core.usecases;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;
import com.aarranz.inventory.core.model.exceptions.ProductNotFoundException;
import com.aarranz.inventory.core.repositories.ProductRepository;

public class SellProductUC {

  private final ProductRepository products;

  public SellProductUC(ProductRepository products) {
    this.products = products;
  }

  public void sell(int quantity, String productName) {

    var product = findProduct(productName);
    checkStock(product, quantity);

    product.removeAmount(quantity);
    products.save(product);
  }

  private void checkStock(Product product,int quantity) {
    if (!product.hasStockFor(quantity)) {
      throw new NotEnoughStockForArticleException();
    }
  }

  private Product findProduct(String productName) {
    var productOp = products.findByName(productName);

    if (productOp.isEmpty()) {
      throw new ProductNotFoundException();
    }

    return productOp.get();
  }
}
