package erickwck.springsecurity_webToken.demo;

import erickwck.springsecurity_webToken.entity.Authority;
import erickwck.springsecurity_webToken.entity.Department;
import erickwck.springsecurity_webToken.entity.Role;
import erickwck.springsecurity_webToken.entity.User;
import erickwck.springsecurity_webToken.repositories.AuthorityRepository;
import erickwck.springsecurity_webToken.repositories.RoleRepository;
import erickwck.springsecurity_webToken.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static erickwck.springsecurity_webToken.entity.Authority.Values.*;
import static erickwck.springsecurity_webToken.entity.Role.Values.*;

@Configuration
public class RBAACSeed implements CommandLineRunner {


    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RBAACSeed(AuthorityRepository authorityRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        //1 - persisti as authorites no banco para depois associa um role com elas
        var expApprove = ensureAuthority(EXP_DELETE);
        var expRead = ensureAuthority(EXP_READ);
        var expCreate = ensureAuthority(EXP_CREATE);
        var expReadAny = ensureAuthority(EXP_READ_ANY);
        var expApproveAny = ensureAuthority(EXP_DELETE_ANY);

        //2 - persisti as roles no banco de dados com as authoritis associada
        var roolerEmployee = ensureRole(ROLE_EMPLOYEE, Set.of(expRead, expCreate));
        var roleManager = ensureRole(ROLE_MANAGER, Set.of(expRead, expCreate, expApprove));
        var roleAdmin = ensureRole(ROLE_ADMIN, Set.of(expRead, expCreate, expApprove, expApproveAny, expReadAny));

        //3 - Cadastra os usuarios com as Roles/Authorities associadas
        ensureUser("emily", "senha", Department.IT, roolerEmployee);
        ensureUser("erick", "senha", Department.IT, roleManager);
        ensureUser("bruno", "senha", Department.ENG, roleManager);
        ensureUser("admin", "123", Department.ENG, roleAdmin);

    }

    private Authority ensureAuthority(String name) {
        return authorityRepository.findByName(name)
                .orElseGet(() -> authorityRepository.save(new Authority(null, name)));
    }

    private Role ensureRole(String name, Set<Authority> authorities) {
        return roleRepository.findByName(name)
                .map(existingRole -> {
                    existingRole.setAuthority(authorities);
                    return roleRepository.save(existingRole);
                })
                .orElseGet(() -> roleRepository.save(new Role(null, name, authorities)));
    }


    public User ensureUser(String username, String password, Department department, Role role) {
        return userRepository
                .findByUsername(username)
                .map(existingUser -> {
                    existingUser.setPassword(passwordEncoder.encode(password));
                    existingUser.setDepartment(department);
                    existingUser.setRoles(Set.of(role));
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> userRepository.save(new User(null, username, passwordEncoder.encode(password), department, Set.of(role))));
    }
}
