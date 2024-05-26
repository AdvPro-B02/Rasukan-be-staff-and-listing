package advpro.b2.rasukanlsp.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Setter;
import lombok.Getter;

@Setter @Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "nominal", nullable = false)
    private int nominal;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "notes", nullable = true)
    private String notes;

    @Column(name = "discount", nullable = true)
    private Integer discount;

    @Column(name = "seller_id", nullable = true)
    private UUID seller;

    public Order(UUID orderId, UUID userId, int nominal, String orderStatus, String paymentStatus, String notes, Integer discount, UUID seller) {
        this.orderId = orderId;
        this.userId = userId;
        this.nominal = nominal;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.notes = notes;
        this.discount = discount;
        this.seller = seller;
    }

    public Order() {

    }
}
