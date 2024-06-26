package lk.ijse.ticket_service.service;

import lk.ijse.ticket_service.dto.TicketDTO;

public interface TicketService {
    boolean existsByTicketNo(String ticketNo);
    void saveTicket(TicketDTO ticketDTO);
}