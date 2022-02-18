package com.learnigPath.rest.webservices.restfulwebservice.user.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList();
    private  static int userCount;

    static
    {
        users.add(new User(1,"ShriRam",new Date()));
        users.add(new User(2,"ShriKrishna",new Date()));
        users.add(new User(3,"ShriHanuman",new Date()));
        users.add(new User(4,"Shri Vasudevay",new Date()));
    }

    public List<User> findAll()
    {
        return users;
    }

    public User save(User user)
    {
        userCount = users.size();
        if(user.getId() == null)
        {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findById(int id)
    {
         for(User u : users)
         {
             if(u.getId().equals(id))
                 return u;
         }
         return null;
    }

    public User update(User updateUser)
    {
        for(User u : users)
        {
            if(u.getId().equals(updateUser.getId()))
            {
                u.setName(updateUser.getName());
                u.setDob(updateUser.getDob());
                return u;
            }
        }
        return null;
    }

    public Boolean delete(int id)
    {
        for(User u : users)
        {
            if(u.getId().equals(id))
            {
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    
}
