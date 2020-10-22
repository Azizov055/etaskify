package az.ibar.etaskify.model;

import az.ibar.etaskify.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Data
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 70)
    private RoleName role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tasks",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "task_id") }
    )
    private List<Task> tasks = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, RoleName roleName) {
        this.username = username;
        this.email = email;
        this.role = roleName;
    }

    public User(String username, String email, String name, String surname, RoleName roleName) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.role = roleName;
    }
}
