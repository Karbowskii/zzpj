package pl.zzpj.esportbetting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String url;

    @OneToMany(mappedBy = "teamA",
            orphanRemoval = true,
            cascade = CascadeType.MERGE)
    private List<Match> matchesA = new ArrayList<>();

    @OneToMany(mappedBy = "teamB",
            orphanRemoval = true,
            cascade = CascadeType.MERGE)
    private List<Match> matchesB = new ArrayList<>();
}
