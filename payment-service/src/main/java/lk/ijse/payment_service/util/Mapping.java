package lk.ijse.payment_service.util;

import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.model.Payment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper mapper;

    // Payment Mapping
    public PaymentDTO toPaymentDTO(Payment payment) {
        return  mapper.map(payment, PaymentDTO.class);
    }
    public Payment toPaymentEntity(PaymentDTO paymentDTO) {
        return  mapper.map(paymentDTO, Payment.class);
    }
    public List<PaymentDTO> toPaymentDTOList(List<Payment> payments) {
        return mapper.map(payments, List.class);
    }
}