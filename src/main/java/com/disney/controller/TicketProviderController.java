package com.disney.controller;

import com.disney.model.Ticket;
import com.disney.repository.TicketProviderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class TicketProviderController {

  @Autowired private TicketProviderRepository ticketProviderRepository;

  @ApiOperation(value = "Get all Ticket")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Ticket> retrieveAllTickets() throws JsonProcessingException {
    return ticketProviderRepository.retrieveAllTickets();
  }

  @ApiOperation(value = "Get Ticket by visualId")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/tickets/{vid}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Ticket retrieveTicketById(@PathVariable String vid) throws JsonProcessingException {
    return ticketProviderRepository.retrieveTicketById(vid);
  }
}
