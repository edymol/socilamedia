package xyz.deved.socialmedia.socialmedia.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user; // This creates a ManyToOne relationship
    @Id
    @GeneratedValue
    private Integer id;
    @Size(min = 10)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    // Getters and setters for the User

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
