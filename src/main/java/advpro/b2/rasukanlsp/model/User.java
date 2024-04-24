package advpro.b2.rasukanlsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean isStaff;

    public User() {
    }

    public User(String id, String username, String email, String password, boolean isStaff) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isStaff = isStaff;
    }
}
