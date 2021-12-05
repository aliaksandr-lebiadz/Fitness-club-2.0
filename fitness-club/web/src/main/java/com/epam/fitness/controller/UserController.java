package com.epam.fitness.controller;

import com.epam.fitness.entity.SignUpRequestDto;
import com.epam.fitness.entity.UserDto;
import com.epam.fitness.entity.user.UserRole;
import com.epam.fitness.exception.ServiceException;
import com.epam.fitness.service.api.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@Validated
@RequestMapping("/user")
public class UserController {

    private static final String USERS_PAGE = "users";
    private static final String USERS_PAGE_URL = "/user/list";
    private static final String USERS_ATTRIBUTE = "userList";
    private static final String SIGN_UP_PAGE_URL_WITH_FAIL_PARAM = "/sign-up?sign_up_fail";
    private static final String LOGIN_PAGE_URL = "/login";

    private final UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUsersPage(Model model){
        List<UserDto> users = service.getAll();
        model.addAttribute(USERS_ATTRIBUTE, users);
        return USERS_PAGE;
    }

    @PostMapping("/set-discount")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String setDiscount(@RequestParam("user_id") int userId,
                              @RequestParam @Min(0) @Max(100) int discount) throws ServiceException{
        UserDto userDto = new UserDto(userId, discount);
        service.update(userDto);
        return ControllerUtils.createRedirect(USERS_PAGE_URL);
    }

    @PostMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public String signUp(@RequestParam @Length(min = 7, max = 30) @Email String email,
                         @RequestParam @Length(min = 5, max = 20) String password,
                         @RequestParam("first_name") @Length(min = 3, max = 30) String firstName,
                         @RequestParam("second_name") @Length(min = 3, max = 30) String secondName) {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(email, password, firstName, secondName, UserRole.CLIENT);
        try{
            service.signUp(signUpRequestDto);
        } catch (ServiceException e) {
            return ControllerUtils.createRedirect(SIGN_UP_PAGE_URL_WITH_FAIL_PARAM);
        }
        return ControllerUtils.createRedirect(LOGIN_PAGE_URL);
    }

}
