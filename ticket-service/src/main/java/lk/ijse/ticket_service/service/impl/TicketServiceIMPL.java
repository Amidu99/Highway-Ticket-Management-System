package lk.ijse.ticket_service.service.impl;

import lk.ijse.ticket_service.dto.TicketDTO;
import lk.ijse.ticket_service.model.Ticket;
import lk.ijse.ticket_service.repo.TicketRepo;
import lk.ijse.ticket_service.service.TicketService;
import lk.ijse.ticket_service.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceIMPL implements TicketService {
    private final TicketRepo repo;
    private final Mapping mapping;

    @Override
    public void saveTicket(TicketDTO ticketDTO) {
        repo.save(mapping.toTicketEntity(ticketDTO));
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        return mapping.toTicketDTOList(repo.findAll());
    }

    @Override
    public List<TicketDTO> getAllPendingTickets(String email) {
        return mapping.toTicketDTOList(repo.getAllPendingTicketsByEmail(email));
    }

    @Override
    public void updateTicketStatus(TicketDTO ticketDTO) {
        Ticket existingTicket = repo.getTicketById(ticketDTO.getId());
        existingTicket.setStatus(ticketDTO.getStatus());
        repo.save(existingTicket);
    }

    @Override
    public TicketDTO getTicketById(String id) {
        return mapping.toTicketDTO(repo.getTicketById(id));
    }

    @Override
    public boolean existsById(String id) {
        return repo.existsById(id);
    }
}