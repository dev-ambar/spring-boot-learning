package com.learnigPath.rest.webservices.restfulwebservice.user.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class UserJPADaoService {

    private static List<User> users = new ArrayList();
    private  static int userCount;

    @Autowired
    private  UserRepository userRepository ;
    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public Optional<User> findById(int id)
    {
         return userRepository.findById(id);
    }

    public User update(User updateUser)
    {
        Optional<User> findUser = userRepository.findById(updateUser.getId());
        if(findUser.isPresent()) {
            findUser.get().setDob(updateUser.getDob());
            findUser.get().setName(updateUser.getName());
            return userRepository.saveAndFlush(findUser.get());
        }
        else
        {
            return null;
        }
    }

    public void delete(int id)
    {
        userRepository.deleteById(id);
    }

    
}
