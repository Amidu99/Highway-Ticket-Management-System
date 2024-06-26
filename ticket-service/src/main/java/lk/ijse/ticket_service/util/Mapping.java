package lk.ijse.ticket_service.util;

import lk.ijse.ticket_service.dto.TicketDTO;
import lk.ijse.ticket_service.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper mapper;

    // Ticket Mapping
    public TicketDTO toTicketDTO(Ticket ticket) {
        return  mapper.map(ticket, TicketDTO.class);
    }
    public Ticket toTicketEntity(TicketDTO ticketDTO) {
        return  mapper.map(ticketDTO, Ticket.class);
    }
    public List<TicketDTO> toTicketDTOList(List<Ticket> tickets) {
        return mapper.map(tickets, List.class);
    }
}