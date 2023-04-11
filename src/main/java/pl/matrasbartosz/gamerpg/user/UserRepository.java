package pl.matrasbartosz.gamerpg.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLoginOrEmailAndPassword(String login, String email, String password);
}
