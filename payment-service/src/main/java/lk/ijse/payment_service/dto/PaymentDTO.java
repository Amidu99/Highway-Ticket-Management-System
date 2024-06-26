package lk.ijse.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDTO implements SuperDTO {
    private String paymentID;
    private String ticketNo;
    private Date paidDate;
    private double amount;
    private String payInfo;
}