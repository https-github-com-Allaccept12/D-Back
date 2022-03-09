package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.domain.account.QAccount;
import TeamDPlus.code.dto.response.FollowResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.account.follow.QFollow.follow;


@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<FollowResponseDto.FollowList> findAllByFollowingId(Long followingId) {
        return queryFactory
                .select(Projections.constructor(FollowResponseDto.FollowList.class,
                        follow.followingId,
                        account.profileImg))
                .from(follow)
                .join(account).on(follow.followingId.eq(account.id))
                .fetch();
    }

    @Override
    public List<FollowResponseDto.FollowList> findAllByFollowerId(Long followerId) {
        return queryFactory
                .select(Projections.constructor(FollowResponseDto.FollowList.class,
                        follow.followingId,
                        account.profileImg))
                .from(follow)
                .join(account).on(follow.followingId.eq(account.id))
                .fetch();
    }
}
