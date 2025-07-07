package org.example.boardserver.service;

import org.example.boardserver.dto.CommentDTO;
import org.example.boardserver.dto.PostDTO;
import org.example.boardserver.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String id , PostDTO postDTO);
    List<PostDTO> getMyPosts(int accountId);
    void updatePosts(PostDTO postDTO);
    void deletePosts (int userId, int postId);

    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(int userId, int commentId);

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deleteTag(int userId, int tagId);
}
