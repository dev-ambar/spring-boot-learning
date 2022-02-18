package com.learnigPath.rest.webservices.restfulwebservice.user.controller;

import com.learnigPath.rest.webservices.restfulwebservice.user.UserNotFoundException;
import com.learnigPath.rest.webservices.restfulwebservice.user.model.User;
import com.learnigPath.rest.webservices.restfulwebservice.user.model.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> getUsers()
    {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUserById(@PathVariable int id)
    {
        User findUser =  userDaoService.findById(id);
        if(findUser==null)
        {
            throw new UserNotFoundException("Id-"+id);
        }
        EntityModel<User> model = EntityModel.of(findUser);
        WebMvcLinkBuilder linkToAllUser = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.
                methodOn(this.getClass()).getUsers());
        return model.add(linkToAllUser.withRel("retrieve all users"));

    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Valid @RequestBody User user)
    {
        User saveUser =  userDaoService.save(user);
         URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}").buildAndExpand(saveUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/users")
    public ResponseEntity updateUser(@RequestBody User user)
    {
        User updateUser =   userDaoService.update(user);
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

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id)
    {
        User findUser =  userDaoService.findById(id);
        if(findUser==null)
        {
            throw new UserNotFoundException("Id-"+id);
        }
        else
        {
           Boolean deleted =   userDaoService.delete(id);
            if(deleted)
            {
                return new ResponseEntity("User with id " +id+"has been successfully deleted",HttpStatus.OK);
            }
            else
                return new ResponseEntity("There are some issue to delete the resource",HttpStatus.EXPECTATION_FAILED);

        }
    }
}
