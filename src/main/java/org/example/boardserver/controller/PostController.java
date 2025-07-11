package org.example.boardserver.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.example.boardserver.aop.LoginCheck;
import org.example.boardserver.dto.CommentDTO;
import org.example.boardserver.dto.PostDTO;
import org.example.boardserver.dto.TagDTO;
import org.example.boardserver.dto.UserDTO;
import org.example.boardserver.dto.response.CommonResponse;
import org.example.boardserver.service.PostService;
import org.example.boardserver.service.impl.PostServiceImpl;
import org.example.boardserver.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@ResponseStatus
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private final UserServiceImpl userService;

    private final PostServiceImpl postService;

    public PostController(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId, @RequestBody PostDTO postDTO) {
        postService.register(accountId, postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","registerPost",postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> getMyPosts(String accountId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyPosts(memberInfo.getId());
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","myPostInfo",postDTOList);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePost(String accountId,
                                                                   @PathVariable(name = "postId") int postId,
                                                                   @RequestBody PostRequest postRequest) {

        UserDTO memberInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(postRequest.getUserId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();

        postService.updatePosts(postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","updatePost",postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePost(String accountId,
                                                                        @PathVariable(name = "postId") int postId,
                                                                        @RequestBody PostDeleteRequest postDeleteRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deletePosts(memberInfo.getId(),postId);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","deletePost",postDeleteRequest);
        return ResponseEntity.ok(commonResponse);

    }

    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> registerPostComment(String accountId, @RequestBody CommentDTO commentDTO) {
        postService.registerComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","registerPostComment",commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> updatePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId,
                                                                        @RequestBody CommentDTO commentDTO) {

        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo!= null)
            postService.updateComment(commentDTO);

        postService.registerComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","registerPostComment",commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deletePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId) {

        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo!= null)
            postService.deleteComment(memberInfo.getId(),commentId);

        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","deletePostComment",commentId);
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> registerPostTag(String accountId, @RequestBody TagDTO tagDTO) {
        postService.registerTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","registerPostTag",tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> updatePostTag(String accountId,
                                                                @PathVariable(name = "tagId") int tagId,
                                                                @RequestBody TagDTO tagDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo!= null)
            postService.updateTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","updatePostTag",tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> deleteTagComment(String accountId,
                                                                        @PathVariable(name = "tagId") int tagId) {

        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo!= null)
            postService.deleteTag(memberInfo.getId(),tagId);

        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","deleteTagComment",tagId);
        return ResponseEntity.ok(commonResponse);
    }


    // -- response 객체 ----

    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTOs;
    }

    // --- request 객체 ----
    @Getter
    @Setter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    @Getter
    @Setter
    private static class PostDeleteRequest {
        private int id;
        private int accountId;
    }

}
