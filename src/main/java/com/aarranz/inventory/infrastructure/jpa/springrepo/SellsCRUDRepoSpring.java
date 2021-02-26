package com.aarranz.inventory.infrastructure.jpa.springrepo;

import com.aarranz.inventory.infrastructure.jpa.entities.SellEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellsCRUDRepoSpring extends CrudRepository<SellEntity, Long> {
}
