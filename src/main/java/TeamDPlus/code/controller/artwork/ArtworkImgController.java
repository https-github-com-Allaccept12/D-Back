package TeamDPlus.code.controller.artwork;


import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.dto.common.CommonDto;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.jwt.UserDetailsImpl;
import TeamDPlus.code.service.artwork.ArtworkMainService;
import TeamDPlus.code.service.artwork.ArtworkMainServiceImpl;
import TeamDPlus.code.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArtworkImgController {

    private final FileProcessService test;
    private final ArtWorkImageRepository artWorkImageRepository;
    private final ArtWorkRepository artWorkRepository;

    private final ArtworkMainService artworkMainService;

    @PostMapping("/artwork/image")
    public void testUpload(@RequestParam(value = "imageFile",required = false,defaultValue = "") List<MultipartFile> imageFile) {
        // json 추가 (컨트롤러단)
        imageFile.forEach((img) -> {
            String s = test.uploadImage(img);
            ArtWorks build = ArtWorks.builder().scope(true).title("test").content("test1").category("testca").build();
            ArtWorks save = artWorkRepository.save(build); // save - entity return
            ArtWorkImage build1 = ArtWorkImage.builder().artWorks(save).artworkImg(s).build();
            artWorkImageRepository.save(build1); // postImage에서 s3 등록
        });
    }

//    // json + file 추가 테스트
//    @PostMapping("/artwork/imageTest")
//    public Long testUploadWithJson(
//            @AuthenticationPrincipal UserDetailsImpl user,
//            @RequestPart(value = "imageFile",required = false) List<MultipartFile> imageFile,
//            @RequestPart(value = "ArtworkRequestDto") ArtWorkRequestDto.ArtWorkCreateAndUpdate dto) {
//        // json 추가 (컨트롤러단)
//        artworkMainService.createArtwork(user.getUser(), dto);
//    }

    @DeleteMapping("/artwork/image")
    public void deleteImage(@RequestBody CommonDto.ImgUrlDto url) {
        int separator = url.getImg_url().lastIndexOf("/") + 1; //uuid 인덱스 번호 확인
        String substring = url.getImg_url().substring(separator);
        test.deleteImage(substring);
    }
}





