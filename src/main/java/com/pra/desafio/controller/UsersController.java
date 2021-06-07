package com.pra.desafio.controller;

import com.pra.desafio.dto.UsersBasicDTO;
import com.pra.desafio.dto.UsersDTO;
import com.pra.desafio.exception.*;
import com.pra.desafio.service.UsersService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    private final UsersService userService;

    Logger logger = LoggerFactory.getLogger(UsersController.class);

    UsersController( UsersService userService) {
        this.userService = userService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/v1/users")
    public List<UsersDTO> all() {
          return  userService.listAllUsers();
    }

    @PostMapping("/api/v1/user")
    @ApiOperation(value = "create new user data searching by email ")
    public UsersDTO newUser(@RequestBody UsersBasicDTO newUser) {
       logger.debug("newUser {} " , newUser.getName());
       var usr = new UsersDTO(newUser);
      return userService.insertUser(usr);

    }

    // Single item

    @GetMapping("/api/v1//user/{id}")
    @ApiOperation(value = "Return user data searching by id ")
    public UsersDTO getOne(@PathVariable Integer id) {
        logger.debug(" getOne {}", id);
        return  userService.getUserByID(id);
     }


    @GetMapping("/api/v1/userByEmail/{email}")
    @ApiOperation(value = "Return user data searching by email ")
    public UsersDTO getOneByEmail(@PathVariable String email) {
        logger.debug("User Email {} " , email);
        return  userService.getUserByEMail(email);
    }

    @PutMapping("/api/v1/user/{id}")
    @ApiOperation(value = "Update user data or create new user ")
    public UsersDTO updateUser(@RequestBody UsersBasicDTO newUser, @PathVariable Integer id) {
       var  usr = new UsersDTO(newUser);
       try{
          userService.getUserByID(id);
          usr.setUserId(id);
         return (userService.updateUser(usr));
       }catch ( UserNotFoundException ex){
          return userService.insertUser(usr);
       }
    }

    @DeleteMapping("/api/v1/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser (id);
    }


    @GetMapping("/api/v1/testExceptionHandling")
    public String testExceptionHandling(@RequestParam int code) {
        switch (code) {
            case 401:
                throw new UnauthorizedException("You are not authorized");
            case 404:
                throw new ResourceNotFoundException("Requested resource is not found.");
            case 400:
                throw new CustomException("Please provide resource id.");
            case 409:
                throw new ResourceAlreadyExists("Resource already exists in DB.");

            default:
                return "Yeah...No Exception.";

        }
    }
}
