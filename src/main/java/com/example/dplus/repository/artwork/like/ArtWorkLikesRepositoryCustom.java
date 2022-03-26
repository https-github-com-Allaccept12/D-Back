package com.example.dplus.repository.artwork.like;

public interface ArtWorkLikesRepositoryCustom {

    boolean existByAccountIdAndArtWorkId(Long accountId,Long artWorkId);
}
