package xyz.deved.socialmedia.socialmedia.user;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount;
    static {
        users.add(new User(++userCount, "Edy", LocalDate.now().minusYears(42)));
        users.add(new User(++userCount, "Tania", LocalDate.now().minusYears(27)));
        users.add(new User(++userCount, "Edith", LocalDate.now().minusYears(21)));
    }
    public List<User> findAll(){

        return users;
    }

    public User findOne(Integer id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
    @PostMapping("/users")
    public User save(User user){
        user.setId(++userCount);
        users.add(user);
        return user;
    }
    public void deleteById(Integer id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}
