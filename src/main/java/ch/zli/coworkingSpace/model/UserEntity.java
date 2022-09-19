package ch.zli.coworkingSpace.model;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "USER")
public class UserEntity implements Serializable {
    @Column(name = "id", updatable = false, nullable = false)
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "job", nullable = false)
    private String job;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name = "password", nullable = false)
    private String password;

}
