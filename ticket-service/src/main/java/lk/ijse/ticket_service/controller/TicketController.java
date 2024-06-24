package lk.ijse.ticket_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    @GetMapping("health")
    public String checkUserHealth(){
        return "Ticket health ok.";
    }
}