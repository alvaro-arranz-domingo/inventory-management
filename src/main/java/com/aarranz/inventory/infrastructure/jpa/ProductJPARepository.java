package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.infrastructure.jpa.mappers.ProductEntityMapper;
import com.aarranz.inventory.infrastructure.jpa.springrepo.ArticleCRUDRepoSpring;
import com.aarranz.inventory.infrastructure.jpa.springrepo.ProductCRUDRepoSpring;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ProductJPARepository implements ProductRepository {

  private final ProductEntityMapper mapper;
  private final ProductCRUDRepoSpring productCRUDRepo;
  private final ArticleCRUDRepoSpring articleCRUDRepo;

  public ProductJPARepository(ProductEntityMapper mapper,
                              ProductCRUDRepoSpring productCRUDRepo,
                              ArticleCRUDRepoSpring articleCRUDRepo) {
    this.mapper = mapper;
    this.productCRUDRepo = productCRUDRepo;
    this.articleCRUDRepo = articleCRUDRepo;
  }

  @Override
  public Optional<Product> findByName(ProductId name) {
    return productCRUDRepo.findById(name.value()).map(mapper::toDom);
  }

  @Override
  public List<Product> getAllProducts() {
    var stream = StreamSupport.stream(productCRUDRepo.findAll().spliterator(), false);
    return stream.map(mapper::toDom).collect(Collectors.toList());
  }

  @Override
  public void saveAll(Set<Product> products) {
    productCRUDRepo.saveAll(products.stream().map(mapper::toEntity).collect(Collectors.toList()));
  }

  @Override
  public void save(Product product) {
    var entity = mapper.toEntity(product);
    productCRUDRepo.save(entity);
  }

  @Override
  public void clearAll() {
    productCRUDRepo.deleteAll();
    articleCRUDRepo.deleteAll();
  }
}
