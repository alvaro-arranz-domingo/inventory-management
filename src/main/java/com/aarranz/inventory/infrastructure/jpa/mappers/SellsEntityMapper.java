package com.aarranz.inventory.infrastructure.jpa.mappers;

import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.model.Sell;
import com.aarranz.inventory.infrastructure.jpa.entities.SellEntity;
import org.springframework.stereotype.Component;

@Component
public class SellsEntityMapper {

  public Sell toDom(SellEntity entity) {
    return new Sell(entity.id, new ProductId(entity.productId), entity.amount);
  }

  public SellEntity toEntity(Sell sell) {
    var entity = new SellEntity();
    entity.id = sell.id();
    entity.amount = sell.amount();
    entity.productId = sell.productId().value();
    return entity;
  }
}
