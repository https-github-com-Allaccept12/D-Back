package com.example.dplus.repository.post.tag;

import com.example.dplus.domain.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> , PostTagRepositoryCustom {

    void deleteAllByPostId(Long postId);

    List<PostTag> findPostTagsByPostId(Long postId);

}
