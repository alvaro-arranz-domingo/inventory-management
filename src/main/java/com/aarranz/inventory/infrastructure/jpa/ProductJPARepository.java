package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ProductJPARepository implements ProductRepository {

  @Override
  public List<Product> getAllProducts() {
    return null;
  }

  @Override
  public void saveAll(Set<Product> products) {

  }

  @Override
  public void save(Product product) {

  }
}
