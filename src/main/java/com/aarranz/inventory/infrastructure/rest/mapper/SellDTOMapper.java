package com.aarranz.inventory.infrastructure.rest.mapper;

import com.aarranz.inventory.core.model.Sell;
import com.aarranz.inventory.infrastructure.rest.dto.SellDTO;
import org.springframework.stereotype.Component;

@Component
public class SellDTOMapper {

  public SellDTO toDTO(Sell sell) {
    var dto = new SellDTO();
    dto.id = sell.id();
    dto.amount = sell.amount();
    dto.productId = sell.productId();
    return dto;
  }
}
