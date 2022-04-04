package com.example.dplus.repository.post.tag;

import com.example.dplus.domain.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    void deleteAllByPostId(Long postId);

}
