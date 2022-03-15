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
                        follow.followerId,
                        account.profileImg))
                .from(follow)
                .join(account).on(account.id.eq(follow.followerId))
                .where(follow.followingId.eq(followingId))
                .fetch();
    }

    @Override
    public List<FollowResponseDto.FollowList> findAllByFollowerId(Long followerId) {
        return queryFactory
                .select(Projections.constructor(FollowResponseDto.FollowList.class,
                        follow.followingId,
                        account.profileImg))
                .from(follow)
                .join(account).on(account.id.eq(follow.followingId))
                .where(follow.followerId.eq(followerId))
                .fetch();
    }

    @Override
    public boolean existsByFollowerIdAndFollowingId(Long accountId, Long followingId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(follow)
                .where(follow.followerId.eq(accountId).and(follow.followingId.eq(followingId)))
                .fetchFirst(); // limit 1

        return fetchOne != null; // 1개가 있는지 없는지 판단 (없으면 null이라 null체크)
    }
}
