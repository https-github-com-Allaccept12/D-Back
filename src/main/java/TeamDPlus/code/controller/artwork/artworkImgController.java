package TeamDPlus.code.controller.artwork;


import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.dto.common.CommonDto;
import TeamDPlus.code.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class artworkImgController {

    private final FileProcessService test;
    private final ArtWorkImageRepository artWorkImageRepository;
    private final ArtWorkRepository artWorkRepository;

    @PostMapping("/artwork/image")
    public void testUpload(@RequestParam(value = "imageFile",required = false,defaultValue = "") List<MultipartFile> imageFile) {
        imageFile.forEach((img) -> {
            String s = test.uploadImage(img);
            ArtWorks build = ArtWorks.builder().scope(true).title("test").content("test1").category("testca").build();
            ArtWorks save = artWorkRepository.save(build);
            ArtWorkImage build1 = ArtWorkImage.builder().artWorks(save).artworkImg(s).build();
            artWorkImageRepository.save(build1);
        });



    }

    @DeleteMapping("/artwork/image")
    public void deleteImage(@RequestBody CommonDto.ImgUrlDto url) {
        int separator = url.getImg_url().lastIndexOf("/") + 1;
        String substring = url.getImg_url().substring(separator);
        test.deleteImage(substring);
    }
}






