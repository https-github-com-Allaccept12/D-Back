package TeamDPlus.code.domain.post.tag;

import TeamDPlus.code.dto.common.CommonDto;

import java.util.List;

public interface PostTagRepositoryCustom {

    List<CommonDto.PostTagDto> findPostTagListByPostId(Long postId);
}
