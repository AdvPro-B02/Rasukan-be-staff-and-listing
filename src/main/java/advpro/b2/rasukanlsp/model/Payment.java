package advpro.b2.rasukanlsp.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "nominal", nullable = false)
    private int nominal;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "payment_status", nullable = true)
    private String paymentNotes;

    @Column(name = "discount", nullable = true)
    private int discount;

    // Constructor
    public Payment() {}

    // Payment pakai diskon dan notes
    public Payment(UUID id, String userId, Order order, int nominal, String paymentStatus, int discount, String paymentNotes) {
        this.id = id;
        this.userId = userId;
        this.order = order;
        this.nominal = nominal;
        this.paymentStatus = paymentStatus;
        this.discount = discount;
        this.paymentNotes = paymentNotes;

        validateAttribute();
    }

    // Payment pakai notes tp gapakai diskon
    public Payment(UUID id, String userId, Order order, int nominal, String paymentStatus, String paymentNotes) {
        this.id = id;
        this.userId = userId;
        this.order = order;
        this.nominal = nominal;
        this.paymentStatus = paymentStatus;
        this.paymentNotes = paymentNotes;

        validateAttribute();
    }

    // Payment pakai diskon tp gapakai notes
    public Payment(UUID id, String userId, Order order, int nominal, String paymentStatus, int discount) {
        this.id = id;
        this.userId = userId;
        this.order = order;
        this.nominal = nominal;
        this.paymentStatus = paymentStatus;
        this.discount = discount;

        validateAttribute();
    }
    // Payment gapakai diskon dan notes
    public Payment(UUID id, String userId, Order order, int nominal, String paymentStatus) {
        this.id = id;
        this.userId = userId;
        this.order = order;
        this.nominal = nominal;
        this.paymentStatus = paymentStatus;

        validateAttribute();
    }

    public String getId() {
        return id.toString();
    }
    public Order getOrder() { return order; }
    public String getUserId() { return userId.toString(); }
    public int getNominal() {
        return nominal;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public String getPaymentNotes() { return paymentNotes; }
    public int getDiscount() {return discount; }

    public void validateAttribute() {
//        if (userId == null || userId.isEmpty()) {
//            throw new IllegalArgumentException("User ID cannot be null or empty.");
//        }
//        if (orderId == null || orderId.isEmpty()) {
//            throw new IllegalArgumentException("Order ID cannot be null or empty.");
//        }
//        if (nominal == null) {
//            throw new IllegalArgumentException("Nominal cannot be null or empty.");
//        }
//        if (!paymentStatus.equals("PENDING") && !paymentStatus.equals("ACCEPTED") && !paymentStatus.equals("REJECTED")) {
//            throw new IllegalArgumentException("Invalid status.");
//        }
    }
}