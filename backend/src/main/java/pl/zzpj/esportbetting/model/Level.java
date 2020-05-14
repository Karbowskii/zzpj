package pl.zzpj.esportbetting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
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

    @OneToMany(mappedBy = "level",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();
}
