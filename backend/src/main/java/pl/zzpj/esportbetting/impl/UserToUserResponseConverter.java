package pl.zzpj.esportbetting.impl;

import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.response.UserResponse;

import java.util.stream.Collectors;

public class UserToUserResponseConverter {
    public static UserResponse convert(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .level(user.getLevel())
                .exp(user.getExp())
                .coins(user.getCoins())
                .authorities(user.getAuthorities()
                        .stream()
                        .map(r -> AuthorityEnum.valueOf(r.getName().toString()))
                        .collect(Collectors.toList()))
                .isActive(user.getIsActive())
                .build();
    }
}
