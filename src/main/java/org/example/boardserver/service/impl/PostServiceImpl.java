package org.example.boardserver.service.impl;

import lombok.extern.log4j.Log4j2;
import org.example.boardserver.dto.CommentDTO;
import org.example.boardserver.dto.PostDTO;
import org.example.boardserver.dto.TagDTO;
import org.example.boardserver.dto.UserDTO;
import org.example.boardserver.mapper.CommentMapper;
import org.example.boardserver.mapper.PostMapper;
import org.example.boardserver.mapper.TagMapper;
import org.example.boardserver.mapper.UserProfileMapper;
import org.example.boardserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {


    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;


    @Override
    public void register(String id, PostDTO postDTO) {

        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo != null) {
            try {
                postMapper.register(postDTO);
                Integer postId = postDTO.getId();
                for(int i=0; i<postDTO.getTagDTOList().size(); i++) {
                    TagDTO tagDTO = postDTO.getTagDTOList().get(i);
                    tagMapper.register(tagDTO);
                    Integer tagId = tagDTO.getId();
                    tagMapper.createPostTag(tagId,postId);
                }
            } catch (RuntimeException e) {
                log.error("register ERROR! {}", postDTO);
                throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요" + postDTO);
            }

        } else {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 게시글 등록 메서드를 확인해주세요" + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = null;

        try {
           postDTOList = postMapper.selectMyPosts(accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {

        if(postDTO!=null && postDTO.getId() !=0) {
            try {
                postMapper.updatePost(postDTO);
            } catch (Exception e) {
                log.error("updatePosts ERROR! {}", postDTO);
                throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요" + postDTO);
            }
        } else {
            log.error("updatePosts ERROR! {}", postDTO);
            throw new RuntimeException("updatePosts ERROR! 게시글 수정 메서드를 확인해주세요" + postDTO);
        }

    }

    @Override
    public void deletePosts(int userId, int postId) {
        if(userId!=0 && postId !=0) {
            try {
                postMapper.deletePost(postId);
            } catch (Exception e) {
                log.error("deletePosts ERROR! {}", postId);
                throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요" + postId);
            }
        } else {
            log.error("deletePosts ERROR! {}", postId);
            throw new RuntimeException("deletePosts ERROR! 게시글 삭제 메서드를 확인해주세요" + postId);
        }
    }

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if (commentDTO.getPostId() != 0) {
            try {
                commentMapper.register(commentDTO);
            } catch (Exception e) {
                log.error("registerComment ERROR! {}", commentDTO);
                throw new RuntimeException("registerComment"+ commentDTO);
            }
        } else {
            log.error("registerComment ERROR! {}", commentDTO);
            throw new RuntimeException("registerComment"+ commentDTO);
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {

        if (commentDTO!= null) {
            try {
                commentMapper.updateComment(commentDTO);
            } catch (Exception e) {
                log.error("updateComment ERROR!");
                throw new RuntimeException("updateComment");
            }
        } else {
            log.error("updateComment ERROR!");
            throw new RuntimeException("updateComment");
        }
    }

    @Override
    public void deleteComment(int userId, int commentId) {
        if (userId != 0 && commentId != 0) {
            try {
                commentMapper.deleteComment(commentId);
            } catch (Exception e) {
                log.error("deleteComment ERROR!{}", commentId);
                throw new RuntimeException("deleteComment "+commentId);
            }
        } else {
            log.error("deleteComment ERROR!{}", commentId);
            throw new RuntimeException("deleteComment "+commentId);
        }

    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            try {
                tagMapper.register(tagDTO);
            } catch (Exception e) {
                log.error("registerTag ERROR! {}", tagDTO);
                throw new RuntimeException("registerTag"+ tagDTO);
            }
        } else {
            log.error("registerTag ERROR! {}", tagDTO);
            throw new RuntimeException("registerTag"+ tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            try {
                tagMapper.updateTag(tagDTO);
            } catch (Exception e) {
                log.error("updateTag ERROR!");
                throw new RuntimeException("updateTag");
            }
        } else {
            log.error("updateTag ERROR!");
            throw new RuntimeException("updateTag");
        }
    }

    @Override
    public void deleteTag(int userId, int tagId) {
        if (userId != 0 && tagId != 0) {
            try {
                tagMapper.deleteTag(tagId);
            } catch (Exception e) {
                log.error("deleteTag ERROR!");
                throw new RuntimeException("deleteTag");
            }
        } else {
            log.error("deleteTag ERROR!");
            throw new RuntimeException("deleteTag");
        }
    }
}
