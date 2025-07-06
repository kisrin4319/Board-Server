package org.example.boardserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.boardserver.dto.UserDTO;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private UserDTO userDTO;
}