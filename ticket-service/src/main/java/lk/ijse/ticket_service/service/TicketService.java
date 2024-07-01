package lk.ijse.ticket_service.service;

import lk.ijse.ticket_service.dto.TicketDTO;
import java.util.List;

public interface TicketService {
    void saveTicket(TicketDTO ticketDTO);
    List<TicketDTO> getAllTickets();
    List<TicketDTO> getAllPendingTickets(String email);
    void updateTicketStatus(TicketDTO ticketDTO);
    TicketDTO getTicketById(String id);
    boolean existsById(String id);
}