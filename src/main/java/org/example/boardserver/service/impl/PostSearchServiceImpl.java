package org.example.boardserver.service.impl;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.example.boardserver.dto.PostDTO;
import org.example.boardserver.dto.request.PostSearchRequest;
import org.example.boardserver.exception.BoardServerException;
import org.example.boardserver.mapper.PostSearchMapper;
import org.example.boardserver.service.PostSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private PostSearchMapper postSearchMapper;

    @Async
    @Cacheable(value = "getPosts", key = "'getPosts'+#postSearchRequest.getName()+ #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {

        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("selectPosts 메서드 실패", e.getMessage());
            throw new BoardServerException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }

        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostByTag(String tagName) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPostByTag(tagName);
        } catch (RuntimeException e) {
            log.error("getPostByTag 메서드 실패", e.getMessage());
        }

        return postDTOList;
    }
}
