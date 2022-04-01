package com.example.dplus.service.artwork.like;


import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.artwork.ArtWorkRepository;
import com.example.dplus.domain.artwork.ArtWorks;
import com.example.dplus.domain.artwork.ArtWorkLikes;
import com.example.dplus.repository.artwork.like.ArtWorkLikesRepository;
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
            throw new ErrorCustomException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        artWorks.getAccount().getRank().upRankScore();
        ArtWorkLikes artWorkLikes = ArtWorkLikes.builder().artWorks(artWorks).account(account).build();
        artWorkLikesRepository.save(artWorkLikes);
    }

    @Transactional
    public void unLike(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (!artWorkLikesRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new ErrorCustomException(ErrorCode.ALREADY_LIKE_ERROR);
        }
        artWorks.getAccount().getRank().downRankScore();
        artWorkLikesRepository.deleteByArtWorksIdAndAccountId(artWorkId,account.getId());
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
    }


}
