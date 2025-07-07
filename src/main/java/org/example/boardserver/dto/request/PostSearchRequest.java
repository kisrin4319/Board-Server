package org.example.boardserver.dto.request;

import lombok.*;
import org.example.boardserver.dto.CategoryDTO;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {
    private int id;
    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private int userId;
    private CategoryDTO.SortStatus sortStatus;

}
