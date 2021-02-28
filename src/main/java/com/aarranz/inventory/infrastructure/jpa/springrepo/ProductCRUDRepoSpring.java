package com.aarranz.inventory.infrastructure.jpa.springrepo;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.infrastructure.jpa.entities.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCRUDRepoSpring extends CrudRepository<ProductEntity, String> {
}
