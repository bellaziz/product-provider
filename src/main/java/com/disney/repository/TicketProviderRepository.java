package com.disney.repository;

import com.disney.config.Constant;
import com.disney.model.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class TicketProviderRepository {

  @Autowired private ObjectMapper mapper;

  public List<Ticket> retrieveAllTickets() throws JsonProcessingException {
    return mapper.readValue(Constant.TICKETS_LIST, new TypeReference<List<Ticket>>() {});
  }

  public Ticket retrieveTicketById(String vid) throws JsonProcessingException {
    return mapper.readValue(Constant.TICKETS_LIST, new TypeReference<List<Ticket>>() {}).stream()
        .filter(p -> p.getTicketVisualId().equals(vid))
        .findFirst()
        .orElse(null);
  }
}
