package TeamDPlus.code.domain.account.follow;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Long countByFollowerId(Long followerId);
    Long countByFollowingId(Long followingId);
}
