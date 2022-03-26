package com.code.service.artwork;

import com.example.dplus.domain.artwork.ArtWorkRepository;
import com.example.dplus.domain.artwork.image.ArtWorkImageRepository;
import com.example.dplus.domain.artwork.like.ArtWorkLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class ArtworkMainPageServiceImplTest {


    @Autowired
    ArtWorkRepository artWorkRepository;

    @Autowired
    ArtWorkImageRepository artWorkImageRepository;

    @Autowired
    ArtWorkLikesRepository artWorkLikesRepository;


}











