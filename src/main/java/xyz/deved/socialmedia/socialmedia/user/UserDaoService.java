package xyz.deved.socialmedia.socialmedia.user;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.function.Predicate;


@Component
public class UserDaoService {
    List<User> users = null;
    public List<User> findAll(){

        return users;
    }

    public List<User> findOne(Integer id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
    @PostMapping("/users")
    public User save(User user){
        user.setId(5);
        users.add(user);
        return user;
    }
    public void deleteById(Integer id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}
