package advpro.b2.rasukanlsp.model.builder;

import advpro.b2.rasukanlsp.model.Payment;

import java.util.UUID;

public class PaymentBuilder {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private Long nominal;
    private String paymentStatus;
    private String paymentNotes;
    private int discount;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setNominal(Long nominal) {
        this.nominal = nominal;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Payment build() {
        return new Payment();
    }
}