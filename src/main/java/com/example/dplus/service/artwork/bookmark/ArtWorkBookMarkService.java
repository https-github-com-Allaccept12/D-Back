package com.example.dplus.service.artwork.bookmark;


import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.BadArgumentsValidException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.artwork.ArtWorkRepository;
import com.example.dplus.domain.artwork.ArtWorks;
import com.example.dplus.domain.artwork.ArtWorkBookMark;
import com.example.dplus.repository.artwork.bookmark.ArtWorkBookMarkRepository;
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
        artWorks.getAccount().getRank().upRankScore();
        ArtWorkBookMark artWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks).account(account).build();
        artWorkBookMarkRepository.save(artWorkBookMark);
    }

    @Transactional
    public void unBookMark(Account account, Long artWorkId) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        if (!artWorkBookMarkRepository.existByAccountIdAndArtWorkId(account.getId(), artWorkId)) {
            throw new ApiRequestException(ErrorCode.ALREADY_BOOKMARK_ERROR);
        }
        artWorks.getAccount().getRank().downRankScore();
        artWorkBookMarkRepository.deleteByArtWorksIdAndAccountId(artWorkId,account.getId());
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
    }
}
