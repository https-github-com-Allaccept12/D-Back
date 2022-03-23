package TeamDPlus.code.service.account;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.BadArgumentsValidException;
import TeamDPlus.code.advice.ErrorCode;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import TeamDPlus.code.domain.account.history.History;
import TeamDPlus.code.domain.account.history.HistoryRepository;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.answer.PostAnswerRepository;
import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.request.AccountRequestDto.UpdateAccountIntro;
import TeamDPlus.code.dto.request.AccountRequestDto.UpdateSpecialty;
import TeamDPlus.code.dto.request.ArtWorkRequestDto.ArtWorkPortFolioUpdate;
import TeamDPlus.code.dto.request.HistoryRequestDto.HistoryUpdateList;
import TeamDPlus.code.dto.response.AccountResponseDto;
import TeamDPlus.code.dto.response.AccountResponseDto.AccountInfo;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountMyPageServiceImpl implements AccountMyPageService {

    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;
    private final ArtWorkRepository artWorkRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final PostAnswerRepository postAnswerRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final PostCommentRepository postCommentRepository;

    //마이페이지
    @Transactional(readOnly = true)
    public AccountInfo showAccountInfo(final Long visitAccountId, final Long accountId) {
        final Account findAccount = getAccount(visitAccountId);
        final Long follower = followRepository.countByFollowerId(findAccount.getId());
        final Long following = followRepository.countByFollowingId(findAccount.getId());
        final boolean isFollow= followRepository.existsByFollowerIdAndFollowingId(visitAccountId,accountId);
        return AccountInfo.from(findAccount,follower,following, isFollow,visitAccountId.equals(accountId));
    }
    //연혁
    @Transactional(readOnly = true)
    public List<HistoryResponseDto.History> showAccountHistory(Long accountId) {
        final List<History> history = historyRepository.findAllByAccountId(accountId);
        return history.stream()
                .map(HistoryResponseDto.History::new)
                .collect(Collectors.toList());
    }
    //작업물 - 포트폴리오
    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtWorkFeed> showAccountCareerFeed(Long lastArtWorkId, Long visitAccountId, Long accountId) {
        final Pageable pageable = PageRequest.of(0,5);
        return artWorkRepository.findByArtWorkImageAndAccountId(lastArtWorkId,pageable,visitAccountId,accountId,true);
    }
    //포트폴리오 - 기본 소개 수정
    @Transactional
    public void updateAccountIntro(UpdateAccountIntro dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateIntro(dto);
    }

    //포트폴리오 - 스킬셋 수정
    @Transactional
    public void updateAccountSpecialty(UpdateSpecialty dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateSpecialty(dto);
    }

    @Transactional
    public void updateAccountHistory(HistoryUpdateList dto, Long accountId) {
        //히스토리 전체 삭제 벌크
        Account account = getAccount(accountId);
        historyRepository.deleteAllByAccountId(account.getId());
        //다시셋팅
        List<History> collect = dto.getHistory().stream()
                .map(data -> History.toEntity(data,account))
                .collect(Collectors.toList());
        historyRepository.saveAll(collect);
    }

    //포트폴리오에서 올리기 한방에 다건
    @Transactional
    public void updateAccountCareerFeedList(ArtWorkPortFolioUpdate dto) {
        //유저가 원하는 작품들 Is_Master를 True로 벌크
        artWorkRepository.updateAllArtWorkIsMasterToTrue(dto.getArtwork_feed());
    }

    //내 작품탭에서 올리기 단건
    @Transactional
    public void masterAccountCareerFeed(Long artWorkId,Account account) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        createValid(account, artWorks);
        artWorks.updateArtWorkIsMaster(true);
    }
    //내 작품탭에서 내리기
    @Transactional
    public void nonMasterAccountCareerFeed(Long artWorkId,Account account) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        createValid(account, artWorks);
        artWorks.updateArtWorkIsMaster(false);
        artWorks.updateArtWorkIsScope(false);
    }

    //작품 보이기
    @Transactional
    public void nonHideArtWorkScope(Long artWorkId, Account account) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        createValid(account,artWorks);
        artWorks.updateArtWorkIsScope(true);
    }
    //작품 숨김
    @Transactional
    public void hideArtWorkScope(Long artWorkId, Account account) {
        ArtWorks artWorks = getArtWorks(artWorkId);
        createValid(account,artWorks);
        artWorks.updateArtWorkIsScope(false);
    }

    //마이페이지/유저작품
    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtWorkFeed> showAccountArtWork(final Long lastArtWorkId,final Long visitAccountId, final Long accountId) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findByArtWorkImageAndAccountId(lastArtWorkId, pageable, visitAccountId, accountId, false);
    }

    //마이페이지/북마크
    @Transactional(readOnly = true)
    public List<ArtWorkResponseDto.ArtWorkBookMark> showAccountArtWorkBookMark(Long lastArtWorkId,final Long accountId) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findArtWorkBookMarkByAccountId(lastArtWorkId,pageable,accountId);
    }

    //마이페이지 대표작품 설정/수정
    @Transactional
    public void setAccountMasterPiece(final Long accountId, final AccountRequestDto.setAccountMasterPiece materPiece) {
        Account account = getAccount(accountId);
        account.setBestArtWork(materPiece.getImg_url_fir(),materPiece.getImg_url_sec());
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new ApiRequestException(ErrorCode.NONEXISTENT_ERROR));
    }
    private void createValid(Account account, ArtWorks artWorks) {
        if(account.getId().equals(artWorks.getId())){
            throw new BadArgumentsValidException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    public List<AccountResponseDto.MyPost> getMyPost(Long accountId, String board) {
        Pageable pageable = PageRequest.of(0,5);
        List<AccountResponseDto.MyPost> myPosts = postRepository.findPostByAccountIdAndBoard(accountId, board, pageable);
        setPostInfo(myPosts);
        return myPosts;
    }

    public List<AccountResponseDto.MyPost> getMyBookMarkPost(Long accountId, String board) {
        Pageable pageable = PageRequest.of(0,5);
        List<AccountResponseDto.MyPost> myBookMarkPost = postRepository.findPostBookMarkByAccountId(accountId, board, pageable);
        setPostInfo(myBookMarkPost);
        return myBookMarkPost;
    }

    public List<AccountResponseDto.MyAnswer> getMyAnswer(Long accountId) {
        Pageable pageable = PageRequest.of(0,5);
        List<AccountResponseDto.MyAnswer> myAnswers = postAnswerRepository.findPostAnswerByAccountId(accountId, pageable);
        return myAnswers;
    }

    public List<AccountResponseDto.MyComment> getMyComment(Long accountId) {
        Pageable pageable = PageRequest.of(0,5);
        List<AccountResponseDto.MyComment> myComments = postCommentRepository.findPostCommentByAccountId(accountId, pageable);
        return myComments;
    }

    private void setPostInfo(List<AccountResponseDto.MyPost> myPosts) {
        myPosts.forEach((myPost) -> {
           Long answerCount = postAnswerRepository.countByPostId(myPost.getPost_id());
           myPost.setAnswer_count(answerCount);

           Long bookMarkCount = postBookMarkRepository.countByPostId(myPost.getPost_id());
           myPost.setBookmark_count(bookMarkCount);
        });
    }

}





















