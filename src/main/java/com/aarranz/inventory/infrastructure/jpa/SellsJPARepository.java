package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.core.model.Sell;
import com.aarranz.inventory.core.repositories.SellRepository;
import com.aarranz.inventory.infrastructure.jpa.mappers.SellsEntityMapper;
import com.aarranz.inventory.infrastructure.jpa.springrepo.SellsCRUDRepoSpring;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SellsJPARepository implements SellRepository {

  private final SellsCRUDRepoSpring sellsCRUDRepoSpring;
  private final SellsEntityMapper mapper;

  public SellsJPARepository(SellsCRUDRepoSpring sellsCRUDRepoSpring, SellsEntityMapper mapper) {
    this.sellsCRUDRepoSpring = sellsCRUDRepoSpring;
    this.mapper = mapper;
  }

  @Override
  public Optional<Sell> findById(Long id) {
    return sellsCRUDRepoSpring.findById(id).map(mapper::toDom);
  }

  @Override
  public Sell save(Sell sell) {
    return mapper.toDom(sellsCRUDRepoSpring.save(mapper.toEntity(sell)));
  }
}
