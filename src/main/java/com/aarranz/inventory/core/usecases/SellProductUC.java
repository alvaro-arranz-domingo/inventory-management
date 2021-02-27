package com.aarranz.inventory.core.usecases;

import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;
import com.aarranz.inventory.core.model.exceptions.ProductNotFoundException;
import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.Sell;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.repositories.SellRepository;
import com.aarranz.inventory.core.transactional.TransactionProvider;

public class SellProductUC {

  private final ProductRepository products;
  private final SellRepository sells;
  private final TransactionProvider transactionProvider;

  public SellProductUC(ProductRepository products, SellRepository sells, TransactionProvider transactionProvider) {
    this.products = products;
    this.sells = sells;
    this.transactionProvider = transactionProvider;
  }

  public Sell sell(int quantity, ProductId productName) {

    var product = findProduct(productName);
    checkStock(product, quantity);

    product.removeAmount(quantity);
    products.save(product);

    return sells.save(Sell.create(productName, quantity));
  }

  private void checkStock(Product product,int quantity) {
    if (!product.hasStockFor(quantity)) {
      throw new NotEnoughStockForArticleException();
    }
  }

  private Product findProduct(ProductId productName) {
    var productOp = products.findByName(productName);

    if (productOp.isEmpty()) {
      throw new ProductNotFoundException();
    }

    return productOp.get();
  }
}
