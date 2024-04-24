package advpro.b2.rasukanlsp.model;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PaymentStatusChangeEvent implements Event {
    private UUID paymentId;
    private String newStatus;

    public PaymentStatusChangeEvent(){}
    public PaymentStatusChangeEvent(UUID paymentId, String newStatus) {
        this.paymentId = paymentId;
        this.newStatus = newStatus;
    }
}
