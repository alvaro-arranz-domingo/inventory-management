package com.aarranz.inventory.infrastructure.jpa.springrepo;

import com.aarranz.inventory.infrastructure.jpa.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCRUDRepoSpring extends CrudRepository<ProductEntity, String> {
}
