package com.learnigPath.rest.webservices.restfulwebservice.user.controller;

import com.learnigPath.rest.webservices.restfulwebservice.user.PostNotFoundException;
import com.learnigPath.rest.webservices.restfulwebservice.user.UserNotFoundException;
import com.learnigPath.rest.webservices.restfulwebservice.user.model.Post;
import com.learnigPath.rest.webservices.restfulwebservice.user.model.PostRepository;
import com.learnigPath.rest.webservices.restfulwebservice.user.model.User;
import com.learnigPath.rest.webservices.restfulwebservice.user.model.UserJPADaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAController {

    @Autowired
    private UserJPADaoService userJPADaoService;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> getUsers()
    {
        return userJPADaoService.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> getUserById(@PathVariable int id)
    {
        Optional<User> findUser =  userJPADaoService.findById(id);
        if(!findUser.isPresent())
        {
            throw new UserNotFoundException("Id-"+id);
        }
        EntityModel<User> model = EntityModel.of(findUser.get());
        WebMvcLinkBuilder linkToAllUser = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.
                methodOn(this.getClass()).getUsers());
        return model.add(linkToAllUser.withRel("retrieve all users"));

    }

    @PostMapping("/jpa/users")
    public ResponseEntity createUser(@Valid @RequestBody User user)
    {
        User saveUser =  userJPADaoService.save(user);
         URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}").buildAndExpand(saveUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/jpa/users")
    public ResponseEntity updateUser(@RequestBody User user)
    {
        User updateUser =   userJPADaoService.update(user);
        if(updateUser==null)
        {
            throw new UserNotFoundException("Id-"+user.getId());
        }
        else
        {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(updateUser.getId()).toUri();
            return new ResponseEntity("User successfully updated",HttpStatus.OK).created(location).build();
        }
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        Optional<User> findUser =  userJPADaoService.findById(id);
        if(!findUser.isPresent())
        {
            throw new UserNotFoundException("Id-"+id);
        }
        else
        {
           userJPADaoService.delete(id);
        }
    }

    @GetMapping("/jpa/users/{userId}/posts")
    public List<Post> getAllPostsByUser(@PathVariable int userId){

         Optional<User> findUser =  userJPADaoService.findById(userId);
         if(!findUser.isPresent())
         {
             throw new UserNotFoundException("user with Id-"+userId+"is not found");
         }
         else
              return findUser.get().getPosts();
    }

    @GetMapping("/jpa/users/{userId}/posts/{postId}")
    public Post getUserPostsById(@PathVariable int userId,@PathVariable int postId){
        Optional<User> findUser =  userJPADaoService.findById(userId);
        if(!findUser.isPresent())
        {
            throw new UserNotFoundException("user with Id- "+userId+ "is not found");
        }
        else
        {
            for(Post post : findUser.get().getPosts())
            {
                if(post.getPostId().equals(postId))
                    return post;
            }
            throw  new PostNotFoundException("this post is no long exist");
        }
    }

   @PostMapping("/jpa/users/{userId}/posts")
    public ResponseEntity createPost(@PathVariable int userId,@RequestBody Post post){

       Optional<User> findUser =  userJPADaoService.findById(userId);
       if(!findUser.isPresent())
       {
           throw new UserNotFoundException("user with Id- "+userId+ "is not found");
       }
       else {
                post.setCreationTime(new Date());
                post.setUser(findUser.get());
                Post createdPostId = postRepository.save(post);

           URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").
                   buildAndExpand(createdPostId.getPostId()).toUri();
           return ResponseEntity.created(location).build();
       }
    }

    @PutMapping("/jpa/users/{userId}/posts")
    public ResponseEntity editPost(@PathVariable int userId ,@RequestBody Post updatedPost){

         boolean hasUpDated = false;
        Optional<User> findUser =  userJPADaoService.findById(userId);
        if(!findUser.isPresent())
        {
            throw new UserNotFoundException("user with Id- "+userId+ "is not found");
        }
        else {
             List<Post> posts = findUser.get().getPosts();
             if(posts.size()==0)
                  throw new PostNotFoundException("user with Id- "+userId+ "is no more have this post");
             for(Post post : posts) {
                 if(post.getPostId().equals(updatedPost.getPostId())) {
                     post.setEditTime(new Date());
                     post.setPost(updatedPost.getPost());
                     hasUpDated = postRepository.saveAndFlush(post)!=null;
                 }
             }
        }
        if(hasUpDated) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").
                    buildAndExpand(updatedPost.getPostId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return  new ResponseEntity("There are some issue to update the resource",HttpStatus.EXPECTATION_FAILED);
    }

    @DeleteMapping("/jpa/users/{userId}/posts/{postId}")
    public ResponseEntity deletePost(@PathVariable int userId,@PathVariable int postId)
    {
        boolean isDeleted = false;
        Optional<User> findUser =  userJPADaoService.findById(userId);
        if(!findUser.isPresent())
        {
            throw new UserNotFoundException("user with Id- "+userId+ "is not found");
        }
        else {
            List<Post> posts = findUser.get().getPosts();
            for(Post post : posts) {
                if(post.getPostId().equals(postId)) {
                    postRepository.delete(post);
                    isDeleted = true;
                }
            }
        }
        if(isDeleted)
            return new ResponseEntity("Post with id " +postId+"has been successfully deleted",HttpStatus.OK);
        else
            return new ResponseEntity("There are some issue to delete the resource",HttpStatus.EXPECTATION_FAILED);
    }

    @DeleteMapping("/jpa/users/{userId}/posts")
    public ResponseEntity deleteAllPostByUser(@PathVariable int userId)
    {
        boolean isDeleted = false;
        Optional<User> findUser =  userJPADaoService.findById(userId);
        if(!findUser.isPresent())
        {
            throw new UserNotFoundException("user with Id- "+userId+ "is not found");
        }
        else {
            List<Post> posts = findUser.get().getPosts();
             postRepository.deleteAll(posts);
             isDeleted = true;
        }

        if(isDeleted)
            return new ResponseEntity(" All Posts have been successfully deleted",HttpStatus.OK);
        else
            return new ResponseEntity("There are some issue to delete the resource",HttpStatus.EXPECTATION_FAILED);
    }
}
