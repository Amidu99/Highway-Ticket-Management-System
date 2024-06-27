package lk.ijse.payment_service.controller;

import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.dto.TicketDTO;
import lk.ijse.payment_service.model.Status;
import lk.ijse.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {
    final static Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    private final RestTemplate restTemplate;
    private TicketDTO toPayTicket;

    @GetMapping("health")
    public String checkPaymentHealth() {
        logger.info("Payment Health Test Passed.");
        return "Payment health ok.";
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> savePayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            validatePayment(paymentDTO);
            paymentDTO.setAmount(toPayTicket.getFineAmount());
            paymentDTO.setPaidDate(new Date());
            paymentService.savePayment(paymentDTO);
            toPayTicket.setStatus(Status.PAID);

            String url = "http://ticket-service/api/v1/ticket/update";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TicketDTO> entity = new HttpEntity<>(toPayTicket, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                paymentService.removePayment(paymentDTO.getTicketNo());
                logger.info(paymentDTO.getTicketNo()+" : payment incomplete.");
                return ResponseEntity.badRequest().body(paymentDTO.getTicketNo()+" : payment incomplete.");
            }

            logger.info(paymentDTO.getTicketNo()+" : payment completed.");
            return ResponseEntity.ok().body(paymentDTO.getTicketNo()+" : payment completed.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void validatePayment(PaymentDTO paymentDTO) {
        if (!Pattern.compile("^[T]-\\d{4}$").matcher(paymentDTO.getTicketNo()).matches()) {
            throw new RuntimeException("Invalid TicketNo");
        }
        if (paymentDTO.getPayInfo().isEmpty()) {
            throw new RuntimeException("Invalid PayInfo");
        }

        String url = "http://ticket-service/api/v1/ticket/get/"+paymentDTO.getTicketNo();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<TicketDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, TicketDTO.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("No ticket found with this ticketNo.");
        }
        if (Objects.requireNonNull(response.getBody()).getStatus().equals(Status.PAID)) {
            throw new RuntimeException(response.getBody().getTicketNo()+" : payment is already completed.");
        }
        toPayTicket = response.getBody();
        logger.info("Ticket validated.");
    }
}