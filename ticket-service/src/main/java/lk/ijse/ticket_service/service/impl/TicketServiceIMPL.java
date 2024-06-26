package lk.ijse.ticket_service.service.impl;

import lk.ijse.ticket_service.dto.TicketDTO;
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

    @Override
    public boolean existsByTicketNo(String ticketNo) {
        return repo.existsByTicketNo(ticketNo);
    }

    @Override
    public void saveTicket(TicketDTO ticketDTO) {
        repo.save(mapping.toTicketEntity(ticketDTO));
    }
}