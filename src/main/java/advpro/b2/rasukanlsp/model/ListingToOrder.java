package advpro.b2.rasukanlsp.model;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="listing_to_order")
@Getter
public class ListingToOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID listingInOrderId;

    @Column(name = "listing_id")
    private UUID listingId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;

    @Setter
    @Column(name = "quantity", nullable = false)
    private int quantity;

    public ListingToOrder() {

    }
    public ListingToOrder(UUID listingToOrderId, UUID listingId, Order order, int quantity) {
        this.listingInOrderId = listingToOrderId;
        this.listingId = listingId;
        this.order = order;
        this.quantity = quantity;
    }

}