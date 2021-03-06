package pl.zzpj.esportbetting.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import pl.zzpj.esportbetting.request.RegisterRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    @ColumnDefault("true")
    private Boolean isActive;

    @ColumnDefault("0")
    @Setter(AccessLevel.NONE) private int exp;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id")
    private Level level;

    @ColumnDefault("100")
    private int coins;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Bet> bets = new ArrayList<>();

    public User(RegisterRequest registerRequest) {
        this.setUsername(registerRequest.getUsername());
        this.setPassword(registerRequest.getPassword());
        this.setEmail(registerRequest.getEmail());
        this.setFirstName(registerRequest.getFirstName());
        this.setLastName(registerRequest.getLastName());
        this.setIsActive(true);
    }

    public void addAuthority(Authority authority) {
        if (authority != null) {
            this.getAuthorities().add(authority);
        }
    }

    public void addCoins(int coinsToAdd) {
        setCoins(getCoins() + coinsToAdd);
    }

    public void removeCoins(int coinsToRemove) {
        if (coinsToRemove > getCoins()) {
            setCoins(0);
        } else {
            setCoins(getCoins() - coinsToRemove);
        }
    }

    public void levelUp(Level nextLevel) {
        setExp(getExp() - getLevel().getExpToNextLevel());
        setLevel(nextLevel);
    }

    public void setExp(int newExp) {
        exp = Math.max(newExp, 0);
    }
    public void addExp(int expToAdd) {
        if (expToAdd > 0) {
            setExp(getExp() + expToAdd);
        }
    }

}

