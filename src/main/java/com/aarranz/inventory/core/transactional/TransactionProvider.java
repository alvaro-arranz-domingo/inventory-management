package com.aarranz.inventory.core.transactional;

import java.util.function.Supplier;

public interface TransactionProvider {

  <T> T executeInTransaction(Supplier<T> supplier);
}
