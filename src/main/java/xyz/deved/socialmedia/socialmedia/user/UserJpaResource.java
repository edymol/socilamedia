package xyz.deved.socialmedia.socialmedia.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.deved.socialmedia.socialmedia.service.UserService;

import java.util.List;

@RestController
public class UserJpaResource {

    private final UserService userService;

    @Autowired
    public UserJpaResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    // Post Endpoints
    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostForUser(@PathVariable int id) {
        return userService.getPostsForUser(id);
    }

    @GetMapping("/jpa/users/{userId}/posts/{postId}")
    public ResponseEntity<Post> retrieveOnePostForUser(
            @PathVariable int userId,
            @PathVariable int postId
    ) {
        return userService.getPostForUser(userId, postId);
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        return userService.createPostForUser(id, post);
    }

    @DeleteMapping("/jpa/users/{userId}/posts/{postId}")
    public void deletePostForUser(
            @PathVariable int userId,
            @PathVariable int postId
    ) {
        userService.deletePostForUser(userId, postId);
    }
}
