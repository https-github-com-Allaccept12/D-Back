package com.example.dplus.repository.artwork.image;

import java.util.List;

public interface ArtworkImageRepositoryCustom {


    List<String> findByAllImageUrl(Long artworkId);

}
