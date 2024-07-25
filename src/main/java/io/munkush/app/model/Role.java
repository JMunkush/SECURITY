package io.munkush.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role{
    @Id
    private String id;

    private String name;
    @ManyToMany
    public List<User> users;

    public Role(String name) {
        this.name = name;
    }
}
