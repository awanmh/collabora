// UserRepository.java

package com.manajemennilai.repository;

import com.manajemennilai.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

<<<<<<< HEAD
=======
/**
 * Repository untuk entitas User.
 */
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}