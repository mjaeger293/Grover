package htl.steyr.maturabeispiel.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private int id;

    @Column(name = "fromDate")
    @NonNull
    private Date from;

    @Column(name = "toDate")
    @NonNull
    private Date to;

    @Column(columnDefinition = "int default 0")
    private int loadings = 0;

    @Column(columnDefinition = "boolean default false")
    private boolean closed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelId")
    @NonNull
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    @NonNull
    private Customer customer;
}
