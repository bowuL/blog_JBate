package com.blog.service;

import com.blog.dao.CommentDao;
import com.blog.model.Comment;
import com.sun.org.apache.xml.internal.security.Init;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author: Still
 * @create: 2020/07/21 14:53
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SensitiveService sensitiveService;

    public void addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        commentDao.insertComment(comment);
    }

    public List<Comment> getCommentByArticleId(int articleId){
        return commentDao.selectByArticleId(articleId);
    }

    public int getComment(int articleId){
        return commentDao.selectCountByArticleId(articleId);
    }

    public void deleteComment(int commentId){
        commentDao.deleteCommentById(commentId);
    }

    public Comment getCommentById(int commentId){
        return commentDao.selectById(commentId);
    }
}
