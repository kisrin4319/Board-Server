package org.example.boardserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.boardserver.dto.UserDTO;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginResponse {
    enum LoginStatus {
        SUCCESS, FAIL, DELETED
    }

    @NonNull
    private LoginStatus result;
    private UserDTO userDTO;

    private static final LoginResponse FAIL = new LoginResponse(LoginStatus.FAIL);

    public static LoginResponse success(UserDTO userDTO) {
        return new LoginResponse(LoginStatus.SUCCESS, userDTO);
    }
}