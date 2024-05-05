package advpro.b2.rasukanlsp.model.builder;

import advpro.b2.rasukanlsp.model.Order;
import advpro.b2.rasukanlsp.enums.OrderStatus;
import advpro.b2.rasukanlsp.enums.PaymentStatus;

import java.util.UUID;

public class OrderBuilder {
    private UUID orderId;
    private UUID userId;
    private int nominal;
    private String orderStatus;
    private String paymentStatus;
    private String notes;
    private Integer discount;

    public OrderBuilder(UUID userId, int nominal) {
        this.userId = userId;
        this.nominal = nominal;
        this.orderStatus = "WAITING_PAYMENT";
        this.paymentStatus = "PENDING";
    }

    public OrderBuilder(UUID orderId, UUID userId, int nominal) {
        this.orderId = orderId;
        this.userId = userId;
        this.nominal = nominal;
    }

    public void setOrderStatus(String orderStatus) {
        if(OrderStatus.contains(orderStatus)){
            this.orderStatus = orderStatus;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPaymentStatus(String paymentStatus) {
        if(PaymentStatus.contains(paymentStatus)){
            this.paymentStatus = paymentStatus;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Order build() {
        return new Order(orderId, userId, nominal, orderStatus, paymentStatus, notes, discount);
    }
}
