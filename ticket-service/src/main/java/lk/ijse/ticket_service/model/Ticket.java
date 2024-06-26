package lk.ijse.ticket_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String ticketNo;
    private Date issueDate;
    private Status status;
    private double fineAmount;
    private String vehicleNo;
}