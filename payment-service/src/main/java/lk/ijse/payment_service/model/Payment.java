package lk.ijse.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "payments")
public class Payment {
    @Id
    private String paymentID;
    private String ticketNo;
    private Date paidDate;
    private double amount;
    private String payInfo;
}