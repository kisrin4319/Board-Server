package org.example.boardserver.service;

import org.example.boardserver.dto.PostDTO;
import org.example.boardserver.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {
    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);
    List<PostDTO> getPostByTag(String tagName);
}
