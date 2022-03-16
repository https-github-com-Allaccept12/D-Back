package TeamDPlus.code.service.artwork.like;


import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikes;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtworkLikeService {

    private final ArtWorkLikesRepository artWorkLikesRepository;
    private final ArtWorkRepository artWorkRepository;

    @Transactional
    public void doLike(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (artWorkLikesRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new ApiRequestException("이미 좋아요한 게시글 입니다.");
        }
        artWorks.getAccount().getRanks().upRankScore();
        ArtWorkLikes artWorkLikes = ArtWorkLikes.builder().artWorks(artWorks).account(account).build();
        artWorkLikesRepository.save(artWorkLikes);
    }

    @Transactional
    public void unLike(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (!artWorkLikesRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new ApiRequestException("이미 좋아요 해지한 게시글 입니다.");
        }
        artWorks.getAccount().getRanks().downRankScore();
        artWorkLikesRepository.deleteByArtWorksIdAndAccountId(artWorkId,account.getId());
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글 입니다."));
    }


}
