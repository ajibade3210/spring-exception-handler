package com.bafoly.ex.user;

import com.bafoly.ex.error.NothingFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public User getUserById(long id) {
        return this.userRepository.findById(id).orElseThrow(()-> new NothingFoundException("User Ain't Existing"));
    }


    public User save(User user) {
        return this.userRepository.save(user);
    }
}
