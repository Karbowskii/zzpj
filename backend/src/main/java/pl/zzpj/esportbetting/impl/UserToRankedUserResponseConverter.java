package pl.zzpj.esportbetting.impl;

import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.response.RankedUserResponse;

public class UserToRankedUserResponseConverter {

    public static RankedUserResponse convert(int place, User user) {
        return new RankedUserResponse(place, UserToUserResponseConverter.convert(user));
    }
}
