package org.example.boardserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.boardserver.dto.PostDTO;
import org.example.boardserver.dto.request.PostSearchRequest;

import java.util.List;

@Mapper
public interface PostSearchMapper {

    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
    public List<PostDTO> getPostByTag(String tagName);
}
