
package com.example.dplus.domain.artwork.image;

import java.util.List;

public interface ArtworkImageRepositoryCustom {


    List<String> findByAllImageUrl(Long artworkId);
}