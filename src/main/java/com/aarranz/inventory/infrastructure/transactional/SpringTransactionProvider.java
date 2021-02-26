package com.aarranz.inventory.infrastructure.transactional;

import com.aarranz.inventory.core.transactional.TransactionProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

@Component
public class SpringTransactionProvider implements TransactionProvider {

  private final TransactionTemplate template;

  public SpringTransactionProvider(TransactionTemplate template) {
    this.template = template;
  }

  @Override
  public <T> T executeInTransaction(Supplier<T> supplier) {
    return template.execute(e -> supplier.get());
  }
}
