package pl.matrasbartosz.gamerpg.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        this.userRepository.save(user);
    }

    public User getUser(String username, String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        Optional<User> userOptional = this.userRepository.findUserByLoginOrEmailAndPassword(username, email, encodedPassword);
        return userOptional.orElseGet(User::new);
    }
}
