package com.learnigPath.rest.webservices.restfulwebservice.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

   @Id
   @GeneratedValue
  private Integer postId;
   @Column
  private String post;
   @Column
  private Date creationTime;
   @Column
  private Date editTime;

   @ManyToOne(fetch = FetchType.LAZY)
   @JsonIgnore
   private User user ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }


    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Post(Integer postId, String post, Date creationTime, Date editTime) {
        this.postId = postId;
        this.post = post;
        this.creationTime = creationTime;
        this.editTime = editTime;
    }

    public Post() {
    }
}
