package advpro.b2.rasukanlsp.model;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "is_staff", nullable = false)
    private Boolean isStaff;

    public User() {}

    public User(String name, String email, String password, String phoneNumber, Long balance, Boolean isStaff) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.isStaff = isStaff;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getBalance() {
        return balance;
    }

    public Boolean isStaff() {
        return isStaff;
    }
}
