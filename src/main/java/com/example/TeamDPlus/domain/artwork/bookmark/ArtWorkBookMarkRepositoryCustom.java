package com.example.TeamDPlus.domain.artwork.bookmark;

public interface ArtWorkBookMarkRepositoryCustom {
    boolean existByAccountIdAndArtWorkId(Long accountId,Long artWorkId);
}
