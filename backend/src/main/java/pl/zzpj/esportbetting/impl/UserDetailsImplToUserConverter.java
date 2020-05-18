package pl.zzpj.esportbetting.impl;

import pl.zzpj.esportbetting.model.User;

import java.util.HashSet;

public class UserDetailsImplToUserConverter {

    public static User convert(UserDetailsImpl userDetails) {
        return User.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .email(userDetails.getEmail())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .authorities(new HashSet<>())
                .isActive(userDetails.isEnabled())
                .exp(userDetails.getExp())
                .level(userDetails.getLevel())
                .coins(userDetails.getCoins())
                .comments(userDetails.getComments())
                .build();
    }
}
