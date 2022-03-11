package TeamDPlus.code.domain.artwork.like;

public interface ArtWorkLikesRepositoryCustom {

    boolean existByAccountIdAndArtWorkId(Long accountId,Long artWorkId);
}
