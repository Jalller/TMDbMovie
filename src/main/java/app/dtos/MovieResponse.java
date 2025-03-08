package app.dtos;

import lombok.Data;
import java.util.List;

@Data
public class MovieResponse {
    private List<MovieDTO> results;

    @Override
    public String toString() {
        return "MovieResponse{" +
                "results=" + results +
                '}';
    }
}
