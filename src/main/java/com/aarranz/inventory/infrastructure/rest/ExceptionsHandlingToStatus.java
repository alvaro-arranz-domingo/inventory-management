package com.aarranz.inventory.infrastructure.rest;

import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;
import com.aarranz.inventory.core.model.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class ExceptionsHandlingToStatus extends DefaultHandlerExceptionResolver {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  void handleIllegalArgumentException(IllegalArgumentException e) { }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  void handleNotEnoughStock(NotEnoughStockForArticleException e) { }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  void handleProductNotFound(ProductNotFoundException e) { }
}
