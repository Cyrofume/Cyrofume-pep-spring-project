package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

//we need to let springbook about this class, aka a controller class
//restful implementation
@RestController
public class SocialMediaController {

    //before beginning we will need to follow spring book annotations

    //we need to map into the restful verbs


    /**
     * The registration will be successful if and only if the 
     * username is not blank, 
     * the password is at least 4 characters long, 
     * and an Account with that username does not already exist. 
     * If all these conditions are met, the response body should contain 
     * a JSON of the Account, including its account_id. 
     * The response status should be 200 OK, which is the default. 
     * 
     * 
     * @param noIdAcount
     * @return 200 if successfully added into db
     * @return 408 if duplicate username
     * @return 400 if not sucessful for any other reason
     */
    @PostMapping(value = "/register") 
    public Account registerAccount(@RequestBody Account noIdAcount) {
        //in our class if no id is presented our entites account class will auto ID it

        return noIdAcount;
        //but there are cases we need to follow as well.
    }
}
