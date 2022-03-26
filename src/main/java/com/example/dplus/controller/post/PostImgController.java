package com.example.dplus.controller.post;

import com.example.dplus.domain.post.image.PostImage;
import com.example.dplus.domain.post.image.PostImageRepository;
import com.example.dplus.domain.post.Post;
import com.example.dplus.domain.post.PostRepository;
import com.example.dplus.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostImgController {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final FileProcessService test;

    @PostMapping("/post/image")
    public void testUpload(@RequestParam(value = "imageFile",required = false,defaultValue = "") List<MultipartFile> imageFile) {
        // json 추가 (컨트롤러단)
        imageFile.forEach((img) -> {
            String s = test.uploadImage(img);
            Post build = Post.builder().title("test").content("test1").category("testca").build();
            Post save = postRepository.save(build); // save - entity return
            PostImage build1 = PostImage.builder().post(save).postImg(s).build();
            postImageRepository.save(build1); // postImage에서 s3 등록
        });
    }

}
