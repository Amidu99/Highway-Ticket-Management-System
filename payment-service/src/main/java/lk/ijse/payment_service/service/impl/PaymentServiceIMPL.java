package lk.ijse.payment_service.service.impl;

import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.repo.PaymentRepo;
import lk.ijse.payment_service.service.PaymentService;
import lk.ijse.payment_service.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceIMPL implements PaymentService {
    private final PaymentRepo repo;
    private final Mapping mapping;

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        repo.save(mapping.toPaymentEntity(paymentDTO));
    }

    @Override
    public void removePayment(String id) {
        repo.deleteById(id);
    }
}