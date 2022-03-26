package com.example.dplus.repository.artwork.bookmark;

public interface ArtWorkBookMarkRepositoryCustom {
    boolean existByAccountIdAndArtWorkId(Long accountId,Long artWorkId);
}
