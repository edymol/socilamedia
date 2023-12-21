package xyz.deved.socialmedia.socialmedia.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.deved.socialmedia.socialmedia.jpa.PostRepository;
import xyz.deved.socialmedia.socialmedia.jpa.UserRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {
    private UserRepository userRepository;
    private PostRepository postRepository;

    // Constructor injection is preferred for injecting dependencies
    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id: "+ id);
        //Below implements HATEOAS for all users
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    // Post Endpoints
    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostForUser(@PathVariable int id) {
        // First the user must be found
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id: "+ id);
        return user.get().getPosts();
    }
    @GetMapping("/jpa/users/{userId}/posts/{postId}")
    public ResponseEntity<Post> retrieveOnePostForUser(
            @PathVariable int userId,
            @PathVariable int postId
    ) {
        // First, find the user
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        User user = userOptional.get();

        // Then, find the post for the user
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }

        Post post = postOptional.get();

        // Check if the post belongs to the specified user
        if (!post.getUser().getId().equals(userId)) {
            throw new PostNotFoundException("Post with id " + postId + " does not belong to user with id " + userId);
        }

        return ResponseEntity.ok(post);
    }


    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        // First the user must be found
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id: "+ id);
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/jpa/users/{userId}/posts/{postId}")
    public void deletePostForUser(
            @PathVariable int userId,
            @PathVariable int postId
    ) {
        // First, find the user
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        User user = userOptional.get();

        // Then, find the post for the user
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }

        Post post = postOptional.get();

        // Check if the post belongs to the specified user
        if (!post.getUser().getId().equals(userId)) {
            throw new PostNotFoundException("Post with id " + postId + " does not belong to user with id " + userId);
        }

        postRepository.deleteById(postId);
    }

}
