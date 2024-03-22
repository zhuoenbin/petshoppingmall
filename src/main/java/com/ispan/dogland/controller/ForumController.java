package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.ArticleDto;
import com.ispan.dogland.model.entity.forum.Articles;
import com.ispan.dogland.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class ForumController {

    @Autowired
    private ForumService fs;

    @GetMapping("/{pageNumber}")
    Page<ArticleDto> forumLobby(@PathVariable Integer pageNumber){
        return fs.showArticlesByPages(pageNumber);
    }

//    @GetMapping("/{articleId}")

}
