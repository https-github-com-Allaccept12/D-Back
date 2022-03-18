package TeamDPlus.code.controller.post;

import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.image.PostImageRepository;
import TeamDPlus.code.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostImgController {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final FileProcessService test;

    // 이미지 등록 (프론트) , 이미지 삭제 및 수정 (백?)

}