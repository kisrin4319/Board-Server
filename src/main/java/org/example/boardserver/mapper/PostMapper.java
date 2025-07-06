package org.example.boardserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.boardserver.dto.CategoryDTO;
import org.example.boardserver.dto.PostDTO;

import java.util.List;

@Mapper
public interface PostMapper {
    public int register(PostDTO postDTO);

    public List<PostDTO> selectMyPosts(int accountId);

    public void updatePost(PostDTO postDTO);

    public void deletePost(int postId);
}
