package lk.ijse.ticket_service.repo;

import lk.ijse.ticket_service.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface TicketRepo extends MongoRepository<Ticket, String> {
    Ticket getTicketById(String id);
    @Query("{ 'status' : 'PENDING', 'email' : ?0 }")
    List<Ticket> getAllPendingTicketsByEmail(String email);
    boolean existsById(String id);
}