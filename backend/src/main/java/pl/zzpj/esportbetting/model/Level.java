package pl.zzpj.esportbetting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
@Table(name = "levels")
public class Level {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ColumnDefault("1")
    @Column(nullable = false)
    private int expToNextLevel;

    @ColumnDefault("1")
    @Column(nullable = false)
    private int maxBets;

    @EqualsAndHashCode.Exclude @ToString.Exclude
    @OneToMany(mappedBy = "level",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
