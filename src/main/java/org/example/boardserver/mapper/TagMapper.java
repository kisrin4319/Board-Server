package org.example.boardserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.boardserver.dto.TagDTO;

@Mapper
public interface TagMapper {

    public int register(TagDTO tagDTO);
    public void updateTag(TagDTO tagDTO);
    public void deleteTag(int tagId);
    public void createPostTag(Integer postId, Integer tagId);
}
