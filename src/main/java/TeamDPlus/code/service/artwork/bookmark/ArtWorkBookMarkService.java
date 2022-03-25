package TeamDPlus.code.service.artwork.bookmark;


import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.BadArgumentsValidException;
import TeamDPlus.code.advice.ErrorCode;
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
            throw new ApiRequestException(ErrorCode.ALREADY_BOOKMARK_ERROR);
        }
        if (artWorks.getAccount().getId().equals(account.getId())) {
            throw new BadArgumentsValidException(ErrorCode.NO_BOOKMARK_MY_POST_ERROR);
        }
        artWorks.getAccount().upRankScore();
        ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
        artWorkBookMarkRepository.save(artWorkBookMark);
    }

    @Transactional
    public void unBookMark(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (!artWorkBookMarkRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_BOOKMARK_ERROR);
        }
        artWorks.getAccount().downRankScore();
        artWorkBookMarkRepository.deleteByArtWorksIdAndAccountId(artWorkId,account.getId());
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
    }
}
