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
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private int id;

    @Column
    @NonNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private List<Model> models;
}
