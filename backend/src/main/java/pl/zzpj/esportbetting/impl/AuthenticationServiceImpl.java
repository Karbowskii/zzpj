package pl.zzpj.esportbetting.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.interfaces.AuthenticationService;
import pl.zzpj.esportbetting.interfaces.JsonWebToken;
import pl.zzpj.esportbetting.model.Authority;
import pl.zzpj.esportbetting.model.User;
import pl.zzpj.esportbetting.interfaces.AuthorityService;
import pl.zzpj.esportbetting.interfaces.UserService;

import java.util.HashSet;
import java.util.Set;

@Service("authService")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JsonWebToken jsonWebTokenUtils;
    private final UserDetailsService userDetailsService;
    private final AuthorityService authorityService;
    private final UserService userService;


    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JsonWebToken jsonWebTokenUtils,
                                     UserDetailsService userDetailsService,
                                     AuthorityService authorityService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jsonWebTokenUtils = jsonWebTokenUtils;
        this.userDetailsService = userDetailsService;
        this.authorityService = authorityService;
        this.userService = userService;
    }

    @Override
    public Pair<User, String> authenticate(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jsonWebTokenUtils.generateJwtToken(authentication);

        User user = userService.getUser(authentication);

        Set<Authority> authoritySet = new HashSet<>();
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            authoritySet.add(authorityService.findByName(AuthorityEnum.valueOf(grantedAuthority.getAuthority())));
        }
        user.setAuthorities(authoritySet);

        return Pair.of(user, jwtToken);
    }
}
