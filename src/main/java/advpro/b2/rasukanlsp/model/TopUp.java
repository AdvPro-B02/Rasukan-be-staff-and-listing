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

    @Column(name = "user", nullable = false)
    UUID user;

    @Column(name = "amount")
    int amount;

    @Enumerated
    TopUpStatus status;

    public TopUp(UUID user, int amount) {
        this.user = user;
        this.amount = amount;
        this.status = TopUpStatus.PENDING;
    }

    public TopUp(UUID id, UUID user, int amount) {
        this(user, amount);
        this.id = id;
    }

    public void setStatus(TopUpStatus status) {
        this.status = status;
    }
}
