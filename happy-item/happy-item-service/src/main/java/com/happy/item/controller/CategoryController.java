package com.happy.item.controller;

import com.happy.common.enums.ExceptionEnum;
import com.happy.common.exception.CommonException;
import com.happy.item.pojo.Category;
import com.happy.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(name="pid")  Long pid){
        List<Category> categories = categoryService.queryCategoriesByPid(pid);
        if(CollectionUtils.isEmpty(categories)){
            throw new CommonException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok(categories);
    }
}
