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
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private int id;

    @Column
    @NonNull
    private String name;

    @Column
    @NonNull
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId")
    @NonNull
    private Brand brand;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "model")
    private List<Rental> rentals;
}
