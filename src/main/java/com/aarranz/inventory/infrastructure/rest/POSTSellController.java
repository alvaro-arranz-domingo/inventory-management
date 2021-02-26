package com.aarranz.inventory.infrastructure.rest;

import com.aarranz.inventory.core.usecases.SellProductUC;
import com.aarranz.inventory.infrastructure.rest.dto.SellDTO;
import com.aarranz.inventory.infrastructure.rest.mapper.SellDTOMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class POSTSellController {

  private final SellProductUC useCase;
  private final SellDTOMapper mapper;

  public POSTSellController(SellProductUC useCase, SellDTOMapper mapper) {
    this.useCase = useCase;
    this.mapper = mapper;
  }

  @PostMapping(path = "/sell", produces = MediaType.APPLICATION_JSON_VALUE)
  public SellDTO sell(@RequestBody SellDTO sellRequest) {
    var sell = useCase.sell(sellRequest.amount, sellRequest.productId);
    return mapper.toDTO(sell);
  }
}
