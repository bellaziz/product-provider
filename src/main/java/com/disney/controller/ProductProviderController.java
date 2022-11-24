package com.disney.controller;

import com.disney.config.Constant;
import com.disney.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductProviderController {

  @Autowired private ObjectMapper mapper;

  @ApiOperation(value = "Get all product")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Product> retrieveAllProducts() throws JsonProcessingException {
    return mapper.readValue(Constant.PRODUCTS_LIST, new TypeReference<List<Product>>() {});
  }

  @ApiOperation(value = "Get Product by Id")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Product retrieveProductById(@PathVariable int id) throws JsonProcessingException {
    return mapper.readValue(Constant.PRODUCTS_LIST, new TypeReference<List<Product>>() {}).stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElse(null);
  }
}
