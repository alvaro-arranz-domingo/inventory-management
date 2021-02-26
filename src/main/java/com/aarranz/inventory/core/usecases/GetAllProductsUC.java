package com.aarranz.inventory.core.usecases;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.transactional.TransactionProvider;

import java.util.List;

public class GetAllProductsUC {

  private final ProductRepository productRepo;
  public GetAllProductsUC(ProductRepository productRepo) {
    this.productRepo = productRepo;
  }

  public List<Product> get() {
    return productRepo.getAllProducts();
  }

}
