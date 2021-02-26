package com.aarranz.inventory.infrastructure.transactional;

import com.aarranz.inventory.core.transactional.TransactionProvider;

import java.util.function.Supplier;

public class DumbTransactionProvider implements TransactionProvider {

  @Override
  public <T> T executeInTransaction(Supplier<T> supplier) {
    return supplier.get();
  }
}
