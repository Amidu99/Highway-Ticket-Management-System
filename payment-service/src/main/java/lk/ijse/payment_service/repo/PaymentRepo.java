package lk.ijse.payment_service.repo;

import lk.ijse.payment_service.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepo extends MongoRepository<Payment, String> {
    void deleteById(String id);
}