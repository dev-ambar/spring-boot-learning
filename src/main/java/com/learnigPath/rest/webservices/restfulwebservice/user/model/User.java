package com.learnigPath.rest.webservices.restfulwebservice.user.model;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    @Size(min = 2 ,message = "Size should be at least 2 character")
    private String name;
    @Column
    @Past(message = "date of birth should be in past")
    private Date dob;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    public User(Integer id, String name, Date dob) {
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                '}';
    }
}
