package lk.ijse.payment_service.dto;

import lk.ijse.payment_service.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDTO implements SuperDTO {
    private String id;
    private Date issueDate;
    private Status status;
    private double fineAmount;
    private String vehicleNo;
    private String email;
}