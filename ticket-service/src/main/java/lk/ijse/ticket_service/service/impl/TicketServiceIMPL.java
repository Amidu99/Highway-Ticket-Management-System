package lk.ijse.ticket_service.service.impl;

import lk.ijse.ticket_service.repo.TicketRepo;
import lk.ijse.ticket_service.service.TicketService;
import lk.ijse.ticket_service.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceIMPL implements TicketService {
    private final TicketRepo repo;
    private final Mapping mapping;

}