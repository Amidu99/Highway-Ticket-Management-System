package lk.ijse.ticket_service.controller;

import lk.ijse.ticket_service.dto.TicketDTO;
import lk.ijse.ticket_service.dto.VehicleDTO;
import lk.ijse.ticket_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ticket")
public class TicketController {
    final static Logger logger = LoggerFactory.getLogger(TicketController.class);
    private final TicketService ticketService;
    private final RestTemplate restTemplate;

    @GetMapping("health")
    public String checkTicketHealth() {
        logger.info("Ticket Health Test Passed.");
        return "Ticket health ok.";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveTicket(@RequestBody TicketDTO ticketDTO) {
        try {
            validateTicket(ticketDTO);
            ticketService.saveTicket(ticketDTO);
            logger.info("Ticket saved.");
            return ResponseEntity.ok().body("Ticket saved.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getOneTicket(@PathVariable String id) {
        boolean isExists = ticketService.existsById(id);
        if (!isExists){
            logger.info(id + " : Ticket does not exist.");
            return ResponseEntity.noContent().build();
        }
        TicketDTO ticketDTO = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticketDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTickets() {
        List<TicketDTO> allTickets = ticketService.getAllTickets();
        logger.info("No of all tickets: "+allTickets.size());
        if (allTickets.size() == 0) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(allTickets);
    }

    @GetMapping("/getAllPending")
    public ResponseEntity<?> getAllPendingTickets(@RequestParam String email) {
        List<TicketDTO> allPendingTickets = ticketService.getAllPendingTickets(email);
        logger.info("No of all pending tickets: "+allPendingTickets.size());
        if (allPendingTickets.size() == 0) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(allPendingTickets);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTicketStatus(@RequestBody TicketDTO ticketDTO) {
        try {
            validateTicket(ticketDTO);
            ticketService.updateTicketStatus(ticketDTO);
            logger.info("Ticket status updated.");
            return ResponseEntity.ok().body("Ticket status updated.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void validateTicket(TicketDTO ticketDTO) {
        if (0 > ticketDTO.getFineAmount()) {
            throw new RuntimeException("Invalid Amount");
        }
        String url = "http://vehicle-service/api/v1/vehicle/get/"+ticketDTO.getVehicleNo();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<VehicleDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, VehicleDTO.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("No vehicle found with this VehicleNo.");
        }
        logger.info("Ticket validated.");
    }
}