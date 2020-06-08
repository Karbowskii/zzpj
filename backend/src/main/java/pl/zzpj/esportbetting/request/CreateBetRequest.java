package pl.zzpj.esportbetting.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBetRequest {
    private int matchId;
    private boolean selectedA;
    private int coins;
}
