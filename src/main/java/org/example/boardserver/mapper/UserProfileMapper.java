package org.example.boardserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.boardserver.dto.UserDTO;

import java.util.Date;

@Mapper
public interface UserProfileMapper {

    UserDTO getUserProfile(@Param("id") String id);

    int insertUserProfile(@Param("id") String id, @Param("password") String password,
                          @Param("nickName") String nickName,
                          @Param("createTime") Date createTime, @Param("updateTime") Date updateTime
                          );


    int deleteUserProfile(@Param("id") String id);

    UserDTO findByIdAndPassword(@Param("id") String id, @Param("password") String password);

    int idCheck(@Param("id") String id);

    int updatePassword(UserDTO user);

    public int updateAddress(UserDTO user);

    int register(UserDTO userProfile);
}
