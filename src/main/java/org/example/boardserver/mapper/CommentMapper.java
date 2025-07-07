package org.example.boardserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.boardserver.dto.CommentDTO;

@Mapper
public interface CommentMapper {

    public int register(CommentDTO commentDTO);

    public void updateComment(CommentDTO commentDTO);

    public void deleteComment(int commentId);

}
