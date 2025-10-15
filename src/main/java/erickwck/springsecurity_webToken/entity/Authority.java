package erickwck.springsecurity_webToken.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_authority")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Authority {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public  Authority(String name){
        this.name = name;
    }

    public static class Values {
        public static final String EXP_READ = "product:read";
        public static final String EXP_CREATE = "product:create";
        public static final String EXP_DELETE = "product:delete";
        public static final String EXP_READ_ANY = "product:read:any";
        public static final String EXP_DELETE_ANY = "product:delete:any";
    }
}
