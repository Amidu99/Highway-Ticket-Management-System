package lk.ijse.payment_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @GetMapping("health")
    public String checkUserHealth(){
        return "Payment health ok.";
    }
}