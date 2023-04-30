package htl.steyr.maturabeispiel.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "grover_user")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private int id;

    @Column
    @NonNull
    private String email;

    @Column
    @NonNull
    private String password;

    @Column
    private String token;

    @Column
    private Date tokenExpirationDate;
}
