package xyz.deved.socialmedia.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.deved.socialmedia.socialmedia.jpa.PostRepository;
import xyz.deved.socialmedia.socialmedia.jpa.UserRepository;
import xyz.deved.socialmedia.socialmedia.user.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public UserService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public EntityModel<User> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(UserJpaResource.class).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    public ResponseEntity<User> createUser(User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<Post> getPostsForUser(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }
        return user.get().getPosts();
    }

    public ResponseEntity<Post> getPostForUser(int userId, int postId) {
        User foundUser = getUserByIdHelper(userId);
        Post foundPost = getEntityByIdHelper(postId, postRepository, PostNotFoundException.class);
        validatePostBelongsToUser(foundPost, foundUser.getId());
        return ResponseEntity.ok(foundPost);
    }

    public ResponseEntity<Object> createPostForUser(int id, Post post) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id: " + id);
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    public void deletePostForUser(int userId, int postId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        User foundUser = user.get();
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }

        Post foundPost = post.get();
        validatePostBelongsToUser(foundPost, foundUser.getId());

        postRepository.deleteById(postId);
    }

    private void validatePostBelongsToUser(Post post, int userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new PostNotFoundException("Post with id " + post.getId() +
                    " does not belong to user with id " + userId);
        }
    }

    private <T> T getEntityByIdHelper(int id, JpaRepository<T, Integer> repository,
                                      Class<? extends RuntimeException> exceptionClass) {
        Optional<T> entity = repository.findById(id);
        if (entity.isEmpty()) {
            throwException(exceptionClass, id);
        }
        return entity.get();
    }

    private User getUserByIdHelper(int id) {
        return getEntityByIdHelper(id, userRepository, UserNotFoundException.class);
    }

    private void throwException(Class<? extends RuntimeException> exceptionClass, int id) {
        try {
            throw exceptionClass.getDeclaredConstructor(String.class)
                    .newInstance("Entity not found with id: " + id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate exception class", e);
        }
    }
}
