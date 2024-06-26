package lk.ijse.ticket_service.dto;

import lk.ijse.ticket_service.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDTO implements SuperDTO {
    private String ticketNo;
    private Date issueDate;
    private Status status;
    private double fineAmount;
    private String vehicleNo;
}