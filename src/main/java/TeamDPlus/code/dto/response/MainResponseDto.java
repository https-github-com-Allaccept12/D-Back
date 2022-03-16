package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class MainResponseDto {

    private List<AccountResponseDto.TopArtist> top_artist;
    private List<ArtWorkResponseDto.ArtworkMain> artwork;

    @Builder
    public MainResponseDto(List<AccountResponseDto.TopArtist> top_artist, List<ArtWorkResponseDto.ArtworkMain> artwork) {
        this.top_artist = top_artist;
        this.artwork = artwork;
    }
}
