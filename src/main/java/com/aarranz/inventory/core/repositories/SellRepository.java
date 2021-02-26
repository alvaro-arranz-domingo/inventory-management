package com.aarranz.inventory.core.repositories;

import com.aarranz.inventory.core.model.Sell;

import java.util.Optional;

public interface SellRepository {

  Optional<Sell> findById(Long id);

  Sell save(Sell sell);
}
