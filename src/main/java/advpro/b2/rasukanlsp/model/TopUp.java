package advpro.b2.rasukanlsp.model;

import advpro.b2.rasukanlsp.enums.TopUpStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "topup")
@Getter
public class TopUp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @Column(name = "amount")
    int amount;

    @Enumerated
    TopUpStatus status;

    public TopUp(UUID user, int amount) {
        this.userId = user;
        this.amount = amount;
        this.status = TopUpStatus.PENDING;
    }

    public TopUp(UUID id, UUID user, int amount) {
        this(user, amount);
        this.id = id;
    }

    public TopUp() {}

    public void setStatus(TopUpStatus status) {
        this.status = status;
    }
}
