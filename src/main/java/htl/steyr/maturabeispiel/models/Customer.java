package htl.steyr.maturabeispiel.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private int id;

    @Column
    @NonNull
    private String name;

    @Column(unique = true)
    @NonNull
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Rental> rentals;
}
