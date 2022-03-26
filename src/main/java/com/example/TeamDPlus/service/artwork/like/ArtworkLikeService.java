package com.example.TeamDPlus.service.artwork.like;


import com.example.TeamDPlus.advice.BadArgumentsValidException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.domain.artwork.ArtWorkRepository;
import com.example.TeamDPlus.domain.artwork.ArtWorks;
import com.example.TeamDPlus.domain.artwork.like.ArtWorkLikes;
import com.example.TeamDPlus.domain.artwork.like.ArtWorkLikesRepository;
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
            throw new BadArgumentsValidException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        artWorks.getAccount().getRank().upRankScore();
        ArtWorkLikes artWorkLikes = ArtWorkLikes.builder().artWorks(artWorks).account(account).build();
        artWorkLikesRepository.save(artWorkLikes);
    }

    @Transactional
    public void unLike(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (!artWorkLikesRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new BadArgumentsValidException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        artWorks.getAccount().getRank().downRankScore();
        artWorkLikesRepository.deleteByArtWorksIdAndAccountId(artWorkId,account.getId());
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new BadArgumentsValidException(ErrorCode.NONEXISTENT_ERROR));
    }


}
