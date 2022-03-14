package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.dto.response.FollowResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long>,FollowRepositoryCustom {

    Long countByFollowerId(Long followerId);
    Long countByFollowingId(Long followingId);
    void deleteByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
