package com.aarranz.inventory.core.repositories;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.ProductId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository {

  Optional<Product> findByName(ProductId name);

  List<Product> getAllProducts();

  void saveAll(Set<Product> products);

  void save(Product product);

  void clearAll();
}
