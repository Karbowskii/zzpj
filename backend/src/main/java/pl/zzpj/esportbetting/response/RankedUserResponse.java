package pl.zzpj.esportbetting.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RankedUserResponse{

    private int place;
    private UserResponse user;
}
