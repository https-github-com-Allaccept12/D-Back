package TeamDPlus.code.service.artwork.comment;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.comment.ArtWorkComment;
import TeamDPlus.code.domain.artwork.comment.ArtWorkCommentRepository;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtWorkCommentService {

    private final ArtWorkCommentRepository artWorkCommentRepository;
    private final ArtWorkRepository artWorkRepository;


    public Long createComment(ArtWorkRequestDto.ArtWorkComment dto, Long artWorkId, Account account) {
        ArtWorks artWorks = artWorkRepository.findById(artWorkId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        ArtWorkComment artWorkComment = ArtWorkComment.builder().artWorks(artWorks).account(account).content(dto.getContent()).build();
        ArtWorkComment save = artWorkCommentRepository.save(artWorkComment);
        return save.getId();
    }

    public void updateComment(Long commentId, ArtWorkRequestDto.ArtWorkComment dto) {
        ArtWorkComment artWorkComment = artWorkCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글이거나, 댓글입니다."));

        artWorkComment.updateComment(dto.getContent());
    }

    public void deleteComment(Long commentId) {
        artWorkCommentRepository.deleteById(commentId);
    }


}
