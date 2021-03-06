package com.example.dplus.repository.post.image;

import com.example.dplus.domain.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    void deleteAllByPostId(Long postId);
    List<PostImage> findByPostId(Long postId);
    void deleteByPostImg(String postImg);
}
