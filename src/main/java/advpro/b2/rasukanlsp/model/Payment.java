package advpro.b2.rasukanlsp.model;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private String userId;

//    @ManyToOne
//    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private String orderId;

    @Column(name = "nominal", nullable = false)
    private Long nominal;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    public Payment() {}

    public Payment(UUID id, String userId, String orderId, Long nominal, String paymentStatus) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.nominal = nominal;
        this.paymentStatus = paymentStatus;
    }

    public String getId() {
        return id.toString();
    }

    public String getUserId() {
        return userId;
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
}