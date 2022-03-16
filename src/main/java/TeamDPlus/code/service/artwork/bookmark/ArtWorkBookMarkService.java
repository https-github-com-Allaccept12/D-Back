package TeamDPlus.code.service.artwork.bookmark;


import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMark;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtWorkBookMarkService {

    private final ArtWorkBookMarkRepository artWorkBookMarkRepository;
    private final ArtWorkRepository artWorkRepository;

    @Transactional
    public void doBookMark(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (artWorkBookMarkRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new ApiRequestException("이미 북마크한 게시글 입니다.");
        }
        artWorks.getAccount().getRanks().upRankScore();
        ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
        artWorkBookMarkRepository.save(artWorkBookMark);
    }

    @Transactional
    public void unBookMark(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (!artWorkBookMarkRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new ApiRequestException("이미 북마크를 해지한 게시글 입니다.");
        }
        artWorks.getAccount().getRanks().downRankScore();
        artWorkBookMarkRepository.deleteByArtWorksIdAndAccountId(artWorkId,account.getId());
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글 입니다."));
    }
}
