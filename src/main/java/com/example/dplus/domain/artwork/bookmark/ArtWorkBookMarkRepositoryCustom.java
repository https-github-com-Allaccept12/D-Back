package com.example.dplus.domain.artwork.bookmark;

public interface ArtWorkBookMarkRepositoryCustom {
    boolean existByAccountIdAndArtWorkId(Long accountId,Long artWorkId);
}
