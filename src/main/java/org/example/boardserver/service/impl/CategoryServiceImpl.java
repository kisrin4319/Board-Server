package org.example.boardserver.service.impl;

import lombok.extern.log4j.Log4j2;
import org.example.boardserver.dto.CategoryDTO;
import org.example.boardserver.mapper.CategoryMapper;
import org.example.boardserver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if (accountId !=null) {
            try {
                categoryMapper.register(categoryDTO);
            } catch (RuntimeException e) {
                log.error("register ERROR! {}",categoryDTO);
                throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메서드를 확인해주세요" + categoryDTO);
            }
        } else {
            log.error("register ERROR! {}",categoryDTO);
            throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메서드를 확인해주세요" + categoryDTO);
        }
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if (categoryDTO != null && categoryDTO.getName() != null) {
            try {
                categoryMapper.updateCategory(categoryDTO);
            } catch (RuntimeException e) {
                log.error("update ERROR! {}",categoryDTO);
                throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryDTO);
            }
        } else {
            log.error("update ERROR! {}",categoryDTO);
            throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryDTO);
        }
    }

    @Override
    public void delete(int categoryId) {
        if(categoryId != 0) {
            try {
                categoryMapper.deleteCategory(categoryId);
            } catch (RuntimeException e) {
                log.error("delete ERROR! {}",categoryId);
                throw new RuntimeException("delete ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryId);
            }

        } else {
            log.error("delete ERROR! {}",categoryId);
            throw new RuntimeException("delete ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryId);
        }
    }
}
