package com.aarranz.inventory.core.usecases;

import com.aarranz.inventory.core.model.*;
import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;
import com.aarranz.inventory.core.model.exceptions.ProductNotFoundException;
import com.aarranz.inventory.core.repositories.ArticlesRepository;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.repositories.SellRepository;
import com.aarranz.inventory.core.transactional.TransactionProvider;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SellProductUC {

  private final ProductRepository products;
  private final ArticlesRepository articles;
  private final SellRepository sells;
  private final TransactionProvider transactionProvider;

  public SellProductUC(ProductRepository products,
                       ArticlesRepository articles,
                       SellRepository sells,
                       TransactionProvider transactionProvider) {
    this.products = products;
    this.articles = articles;
    this.sells = sells;
    this.transactionProvider = transactionProvider;
  }

  public Sell sell(int quantity, ProductId productName) {

    var sell = transactionProvider.executeInTransaction(() -> {

      var product = findProduct(productName);
      var productArtsIds = StreamSupport.stream(product.requiredArticles().spliterator(), false)
          .map(ArticleRequirement::articleId)
          .collect(Collectors.toList());
      var productArts = articles.findAllById(productArtsIds);

      var notEnoughStock = productArts.stream().anyMatch(a -> !articleHasStockForProduct(a, product));

      if (notEnoughStock) {
        throw new NotEnoughStockForArticleException();
      }

      productArts.forEach(a -> reduceStockOfArticle(a, product, quantity));

      articles.saveAll(new HashSet<>(productArts));

      return sells.save(Sell.create(productName, quantity));
    });

    var productIdNeedUpdate = products.getProductsToBeUpdatedWhenSellProduct(productName);
    for (var id : productIdNeedUpdate) {
      products.updateProductStock(id);
    }

    return sell;
  }

  private boolean articleHasStockForProduct(Article article, Product product) {
    var articleRequirement = product.getRequiredArticleById(article.id());
    return article.stock() >= articleRequirement.get().amount();
  }

  private void reduceStockOfArticle(Article article, Product product, int quantity) {
    var articleRequirement = product.getRequiredArticleById(article.id());
    article.reduceStockIn(articleRequirement.get().amount() * quantity);
  }

  private Product findProduct(ProductId productName) {
    var productOp = products.findByName(productName);

    if (productOp.isEmpty()) {
      throw new ProductNotFoundException();
    }

    return productOp.get();
  }

}
