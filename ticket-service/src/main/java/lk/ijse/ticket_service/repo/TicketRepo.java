package lk.ijse.ticket_service.repo;

import lk.ijse.ticket_service.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepo extends MongoRepository<Ticket, String> {
    boolean existsByTicketNo(String ticketNo);
}