package lk.ijse.ticket_service.service;

import lk.ijse.ticket_service.dto.TicketDTO;
import java.util.List;

public interface TicketService {
    boolean existsByTicketNo(String ticketNo);
    void saveTicket(TicketDTO ticketDTO);
    TicketDTO getTicketByTicketNo(String ticketNo);
    List<TicketDTO> getAllTickets();
}