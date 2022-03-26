package com.example.TeamDPlus.service.artwork.comment;


import com.example.TeamDPlus.advice.BadArgumentsValidException;
import com.example.TeamDPlus.advice.ErrorCode;
import com.example.TeamDPlus.domain.account.Account;
import com.example.TeamDPlus.domain.artwork.ArtWorkRepository;
import com.example.TeamDPlus.domain.artwork.ArtWorks;
import com.example.TeamDPlus.domain.artwork.comment.ArtWorkComment;
import com.example.TeamDPlus.domain.artwork.comment.ArtWorkCommentRepository;
import com.example.TeamDPlus.dto.request.ArtWorkRequestDto;
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
        ArtWorks artWorks = artWorkRepository.findById(artWorkId).orElseThrow(() -> new BadArgumentsValidException(ErrorCode.NONEXISTENT_ERROR));
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
                .orElseThrow(() -> new BadArgumentsValidException(ErrorCode.NONEXISTENT_ERROR));
        if (!artWorkComment.getAccount().getId().equals(accountId)) {
            throw new BadArgumentsValidException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return artWorkComment;
    }


}
