package advpro.b2.rasukanlsp.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    private String userId;

    private String orderId;

    private Long nominal;

    private String paymentStatus;

    public Payment() {}

    public Payment(UUID id, String userId, String orderId, Long nominal, String paymentStatus) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.nominal = nominal;
        this.paymentStatus = paymentStatus;

        validateAttribute();
    }

    public String getId() {
        return id.toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public String nominal() {
        return nominal.toString();
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void validateAttribute() {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty.");
        }
        if (nominal == null) {
            throw new IllegalArgumentException("Nominal cannot be null or empty.");
        }
        if (!paymentStatus.equals("PENDING") && !paymentStatus.equals("ACCEPTED") && !paymentStatus.equals("REJECTED")) {
            throw new IllegalArgumentException("Invalid status.");
        }
    }
}