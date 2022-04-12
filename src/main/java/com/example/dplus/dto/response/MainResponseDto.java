package com.example.dplus.dto.response;

import com.example.dplus.dto.response.AccountResponseDto.TopArtist;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtworkMain;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class MainResponseDto {

    private List<TopArtist> top_artist;
    private List<ArtworkMain> artwork;

    @Builder
    public MainResponseDto(List<TopArtist> top_artist, List<ArtworkMain> artwork) {
        this.top_artist = top_artist;
        this.artwork = artwork;
    }

    public static MainResponseDto of(List<TopArtist> top_artist, List<ArtworkMain> artwork) {
        return MainResponseDto.builder()
                .top_artist(top_artist)
                .artwork(artwork)
                .build();
    }
}
