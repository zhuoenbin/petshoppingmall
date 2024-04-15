package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Comment;
import com.ispan.dogland.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    //尋找(Comment、Users、Product)三張表的欄位資料
//    @Query("SELECT c.commentId, c.star, c.quality, c.commentary, c.photoPath, c.postDate, u.userId, u.lastName, u.firstName FROM Comment c JOIN c.users u")
//    List<Object[]> findAllCommentsWithUserDetails();

    //依照傳進來的ProductId尋找(Comment、Users、Product)三張表的欄位資料
    @Query("SELECT c.commentId, c.star, c.status, c.commentary, c.photoPath, c.postDate, u.userId, u.lastName, u.firstName " +
            "FROM Comment c JOIN c.users u " +
            "WHERE c.product.productId = :productId")
    List<Object[]> findCommentWithUserByProductId(Integer productId);


    // 使用 JOIN FETCH 擷取與評論關聯的使用者和產品資訊
//    @Query("SELECT c FROM Comment c JOIN FETCH c.users JOIN FETCH c.product")
//    List<Comment> findAllCommentsWithUsersAndProduct();

    //尋找productID對應的Comment(但是沒有User資訊)
//    Comment findByProduct(Product productId);

    //這個JOIN FETCH沒效果
//    @Query("SELECT c FROM Comment c JOIN FETCH c.product p JOIN FETCH c.users u WHERE p.productId = :productId")
//    List<Comment> findCommentsByProductId(Integer productId);

}
