package com.example.dplus.service.artwork.comment;


import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.artwork.ArtWorkRepository;
import com.example.dplus.domain.artwork.ArtWorks;
import com.example.dplus.domain.artwork.ArtWorkComment;
import com.example.dplus.repository.artwork.comment.ArtWorkCommentRepository;
import com.example.dplus.dto.request.ArtWorkRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtWorkCommentService {

    private final ArtWorkCommentRepository artWorkCommentRepository;
    private final ArtWorkRepository artWorkRepository;


    @Transactional
    public Long createComment(ArtWorkRequestDto.ArtWorkComment dto, Long artWorkId, Account account) {
        ArtWorks artWorks = artWorkRepository.findById(artWorkId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        ArtWorkComment artWorkComment = ArtWorkComment.builder().artWorks(artWorks).account(account).content(dto.getContent()).build();
        ArtWorkComment save = artWorkCommentRepository.save(artWorkComment);
        return save.getId();
    }

    @Transactional
    public void updateComment(Long commentId, ArtWorkRequestDto.ArtWorkComment dto,Long accountId) {
        ArtWorkComment artWorkComment = commentValid(commentId, accountId);
        artWorkComment.updateComment(dto.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId,Long accountId) {
        commentValid(commentId, accountId);
        artWorkCommentRepository.deleteById(commentId);
    }

    private ArtWorkComment commentValid(Long commentId, Long accountId) {
        ArtWorkComment artWorkComment = artWorkCommentRepository.findById(commentId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        if (!artWorkComment.getAccount().getId().equals(accountId)) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return artWorkComment;
    }


}
