package com.bafoly.ex.user;

import com.bafoly.ex.error.AppError;
import com.bafoly.ex.error.NothingFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id){
            return this.userService.getUserById(id);
    }

//    @ExceptionHandler(NothingFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public AppError handleNothingFoundException(NothingFoundException exception, HttpServletRequest request){
//        AppError error = new AppError(404, exception.getMessage(),request.getServletPath(), "Head Back To Home Page");
//        return error;
//    }

    @PostMapping("/users")
    public  User createUser(@Valid @RequestBody User user){
        return this.userService.save(user);
    }
}
