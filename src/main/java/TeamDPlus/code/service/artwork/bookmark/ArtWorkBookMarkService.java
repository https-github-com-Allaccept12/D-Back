package TeamDPlus.code.service.artwork.bookmark;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMark;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtWorkBookMarkService {

    private final ArtWorkBookMarkRepository artWorkBookMarkRepository;
    private final ArtWorkRepository artWorkRepository;


    public void doBookMark(Account account, Long artWorkId) {
        ArtWorks artWorks = artWorkRepository.findById(artWorkId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        if (artWorkBookMarkRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new IllegalArgumentException("이미 북마크한 게시글 입니다.");
        }
        ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
        artWorkBookMarkRepository.save(artWorkBookMark);
    }

    public void unBookMark(Account account, Long artWorkId) {
        artWorkBookMarkRepository.deleteByArtWorksIdAndAccountId(artWorkId,account.getId());
    }
}
