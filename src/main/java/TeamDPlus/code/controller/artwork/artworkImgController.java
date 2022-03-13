package TeamDPlus.code.controller.artwork;


import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.dto.common.CommonDto;
import TeamDPlus.code.service.file.FileProcessService;
import TeamDPlus.code.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class artworkImgController {

    private final FileProcessService test;
    private final ArtWorkImageRepository artWorkImageRepository;
    private final ArtWorkRepository artWorkRepository;

    @PostMapping("/artwork/image")
    public void testUpload(@RequestParam("imageFile") MultipartFile imageFile) {
        String s = test.uploadImage(imageFile);

        ArtWorks build = ArtWorks.builder().scope(true).title("test").content("test1").category("testca").build();
        ArtWorks save = artWorkRepository.save(build);
        ArtWorkImage build1 = ArtWorkImage.builder().artWorks(save).artworkImg(s).build();
        artWorkImageRepository.save(build1);

    }

    @DeleteMapping("/artwork/image")
    public void deleteImage(@RequestBody CommonDto.ImgUrlDto url) {
        log.info(url.getImg_url());
        test.deleteImage(url.getImg_url());
    }
}






