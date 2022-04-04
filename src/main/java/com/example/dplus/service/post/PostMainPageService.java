package com.example.dplus.service.post;

import com.example.dplus.dto.request.PostRequestDto;
import com.example.dplus.dto.response.PostMainReccomendationDto;
import com.example.dplus.dto.response.PostMainResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import com.example.dplus.dto.response.PostSearchResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostMainPageService {

    // 디모페이지 추천피드
    PostMainReccomendationDto showPostRecommendation(String board);

    // 디모페이지 카테고리별 최신순
    PostMainResponseDto showPostMain(Long lastPostId, String board, String category);

    // 디모페이지 카테고리별 좋아요
    PostMainResponseDto showPostMainLikeSort(int start, String board, String category);

    // 게시글 작성
    int createPost(Long accountId, PostRequestDto.PostCreate dto, List<MultipartFile> imageFile);

    // 게시글 검색
    PostSearchResponseDto findBySearchKeyWord(String keyword, Long lastArtWorkId, String board);

    // 상세 게시글
    PostResponseDto.PostDetailPage showPostDetail(Long accountId, Long postId);

    // 게시글 수정
    Long updatePost(Long accountId, Long postId, PostRequestDto.PostUpdate dto, List<MultipartFile> imgFile);

    // 게시글 삭제
    void deletePost(Long accountId, Long postId,  String board);

    // 상세 질문글
    PostResponseDto.PostAnswerDetailPage detailAnswer(Long accountId, Long postId);

    // 유사한 질문 리스트
    List<PostResponseDto.PostSimilarQuestion> similarQuestion(String category,Long postId);

}
