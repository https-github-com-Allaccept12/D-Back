package com.example.dplus.service.account;

import com.example.dplus.repository.account.follow.FollowRepository;
import com.example.dplus.domain.account.History;
import com.example.dplus.repository.account.history.HistoryRepository;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.artwork.ArtWorkRepository;
import com.example.dplus.domain.artwork.ArtWorks;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.dto.request.AccountRequestDto;
import com.example.dplus.dto.request.AccountRequestDto.UpdateAccountIntro;
import com.example.dplus.dto.request.AccountRequestDto.UpdateSpecialty;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkPortFolioUpdate;
import com.example.dplus.dto.request.HistoryRequestDto.HistoryUpdateList;
import com.example.dplus.dto.response.AccountResponseDto;
import com.example.dplus.dto.response.AccountResponseDto.AccountInfo;
import com.example.dplus.dto.response.ArtWorkResponseDto;
import com.example.dplus.dto.response.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
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
    public AccountInfo showAccountInfo(Long visitAccountId, Long accountId) {
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
    public List<ArtWorkResponseDto.ArtWorkFeed> showAccountCareerFeed(Long LastArtWorkId,Long visitAccountId, Long accountId) {
        Pageable pageable = PageRequest.of(0,10);
        return artWorkRepository.findByArtWorkImageAndAccountId(LastArtWorkId,pageable,visitAccountId,accountId,true);
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

    @Transactional(readOnly = true)
    public List<AccountResponseDto.MyPost> getMyPost(Long accountId, String board) {
        Pageable pageable = PageRequest.of(0,5);
        List<AccountResponseDto.MyPost> myPosts = postRepository.findPostByAccountIdAndBoard(accountId, board, pageable);
        if (board.equals("QNA")) {
            setQnaInfo(myPosts);
        } else if (board.equals("INFO")) {
            setPostInfo(myPosts);
        }
        return myPosts;
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDto.MyPost> getMyBookMarkPost(Long accountId, String board) {
        Pageable pageable = PageRequest.of(0,5);
        List<AccountResponseDto.MyPost> myBookMarkPost = postRepository.findPostBookMarkByAccountId(accountId, board, pageable);
        setPostInfo(myBookMarkPost);
        return myBookMarkPost;
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDto.MyAnswer> getMyAnswer(Long accountId) {
        Pageable pageable = PageRequest.of(0,5);
        return postAnswerRepository.findPostAnswerByAccountId(accountId, pageable);
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDto.MyComment> getMyComment(Long accountId) {
        Pageable pageable = PageRequest.of(0,5);
        return postCommentRepository.findPostCommentByAccountId(accountId, pageable);
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));
    }

    private ArtWorks getArtWorks(Long artWorkId) {
        return artWorkRepository.findById(artWorkId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
    }
    private void createValid(Account account, ArtWorks artWorks) {
        if(account.getId().equals(artWorks.getId())){
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    private void setQnaInfo(List<AccountResponseDto.MyPost> myPosts) {
        myPosts.forEach((myPost) -> {
            Long answerCount = postAnswerRepository.countByPostId(myPost.getPost_id());
            myPost.setAnswer_count(answerCount);

            Long bookMarkCount = postBookMarkRepository.countByPostId(myPost.getPost_id());
            myPost.setBookmark_count(bookMarkCount);
        });
    }

    private void setPostInfo(List<AccountResponseDto.MyPost> myPosts) {
        myPosts.forEach((myPost) -> {
            Long commentCount = postCommentRepository.countByPostId(myPost.getPost_id());
            myPost.setAnswer_count(commentCount);

            Long bookMarkCount = postBookMarkRepository.countByPostId(myPost.getPost_id());
            myPost.setBookmark_count(bookMarkCount);
        });
    }

}





















