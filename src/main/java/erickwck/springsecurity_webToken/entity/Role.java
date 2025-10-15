package erickwck.springsecurity_webToken.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "tb_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_role_authority",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authority = new HashSet<>();

    public static class Values {
        public static final String ROLE_CUSTOMER = "CUSTOMER";
        public static final String ROLE_EMPLOYEE = "EMPLOYEE";
        public static final String ROLE_MANAGER = "MANAGER";
        public static final String ROLE_ADMIN =  "ADMIN";
    }
}
