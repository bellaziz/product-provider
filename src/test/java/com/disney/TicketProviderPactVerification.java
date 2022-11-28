package com.disney;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.disney.model.Ticket;
import com.disney.repository.TicketProviderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import org.apache.http.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Provider("TicketProvider")
@PactFolder("pacts")
class TicketProviderPactVerification {

  @LocalServerPort
  int port;

  @MockBean
  TicketProviderRepository ticketProviderRepository;

  @BeforeEach
  void setUp(PactVerificationContext context) {
    context.setTarget(new HttpTestTarget("localhost", port));
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void verifyPact(PactVerificationContext context, HttpRequest request) {
    //replaceAuthHeader(request);
    context.verifyInteraction();
  }

  private void replaceAuthHeader(HttpRequest request) {
    if (request.containsHeader("Authorization")) {
      String header = "Bearer " + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());
      request.removeHeaders("Authorization");
      request.addHeader("Authorization", header);
    }
  }

  @State("Tickets exist")
  public void getAllTickets() throws JsonProcessingException {
    Mockito.when(ticketProviderRepository.retrieveAllTickets()).thenReturn(
        Arrays.asList(new Ticket(
                "000000001",
                "Micky",
                false,
                "annual-pass",
                "annual pass ticket",
                3,
                LocalDateTime.of(2022, 11, 24, 15, 41, 25),
                LocalDateTime.of(2023, 11, 24, 15, 41, 25)),
            new Ticket(
                "000000002",
                "Micky",
                false,
                "pass-en-scene",
                "pass en scene ticket",
                5,
                LocalDateTime.of(2022, 11, 24, 15, 41, 25),
                LocalDateTime.of(2023, 11, 24, 15, 41, 25))));
  }

  @State("Ticket visualId 000000001 exists")
  public void getSpecificTicket() throws JsonProcessingException {
    Mockito.when(ticketProviderRepository.retrieveTicketById("000000001")).thenReturn(
        new Ticket(
                "000000001",
                "Micky",
                false,
                "annual-pass",
                "annual pass ticket",
                3,
                LocalDateTime.of(2022, 11, 24, 15, 41, 25),
                LocalDateTime.of(2023, 11, 24, 15, 41, 25)));
  }

}
