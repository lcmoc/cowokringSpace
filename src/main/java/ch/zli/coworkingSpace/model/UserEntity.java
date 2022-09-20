package ch.zli.coworkingSpace.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "USER")
public class UserEntity implements Serializable {
    public UserEntity(String firstname, String lastname, String email, String password, String role, String description, String job, String gender) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.description = description;
        this.job = job;
        this.gender = gender;
    }

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

    @Column(name = "role")
    private String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;
}
