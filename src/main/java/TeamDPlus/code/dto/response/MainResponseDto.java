package TeamDPlus.code.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

public class MainResponseDto {

    private List<AccountResponseDto.TopArtist> top_artist;
    private Page<ArtWorkResponseDto.ArtworkMain> artwork;

    @Builder
    public MainResponseDto(List<AccountResponseDto.TopArtist> top_artist, Page<ArtWorkResponseDto.ArtworkMain> artwork) {
        this.top_artist = top_artist;
        this.artwork = artwork;
    }
}
