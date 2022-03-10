package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.dto.response.FollowResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Long countByFollowerId(Long followerId);
    Long countByFollowingId(Long followingId);


}
