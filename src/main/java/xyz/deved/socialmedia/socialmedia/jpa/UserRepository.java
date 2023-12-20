package xyz.deved.socialmedia.socialmedia.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.deved.socialmedia.socialmedia.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
