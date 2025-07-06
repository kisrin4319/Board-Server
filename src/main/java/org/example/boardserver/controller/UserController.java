package org.example.boardserver.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.example.boardserver.dto.UserDTO;
import org.example.boardserver.dto.request.UserDeleteId;
import org.example.boardserver.dto.request.UserLoginRequest;
import org.example.boardserver.dto.request.UserUpdatePasswordRequest;
import org.example.boardserver.dto.response.LoginResponse;
import org.example.boardserver.dto.response.UserInfoResponse;
import org.example.boardserver.service.impl.UserServiceImpl;
import org.example.boardserver.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserServiceImpl userService;

    private static LoginResponse loginResponse;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
        if (userDTO.hasNullDataBeforeRegister(userDTO)) {
            throw  new RuntimeException("회원가입 정보를 확인해주세요");
        }
        userService.register(userDTO);
    }
    @PostMapping("sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest userLoginrequest, HttpSession session) {

        String id = userLoginrequest.getUserId();
        String password = userLoginrequest.getPassword();
        UserDTO userInfo = userService.login(id, password);

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else {
            if (userInfo.getStatus() == UserDTO.Status.ADMIN) {
                SessionUtil.setLoginAdminId(session,id);
            } else {
                SessionUtil.setLoginMemberId(session, id);
            }
        }
        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null) id = SessionUtil.getLoginAdminId(session);
        UserDTO memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    public ResponseEntity<LoginResponse> updateUserPassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
                                                            HttpSession session) {

        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(id, beforePassword, afterPassword);
            ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));

        } catch (IllegalArgumentException e) {
            log.error("updateUserPassword ERROR : {}", e.getMessage());
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @DeleteMapping("delete")
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId, HttpSession session) {

        ResponseEntity<LoginResponse> responseEntity = null;

        String id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(id,userDeleteId.getPassword());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);

        } catch (RuntimeException e) {
            log.error("deleteId ERROR");
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
