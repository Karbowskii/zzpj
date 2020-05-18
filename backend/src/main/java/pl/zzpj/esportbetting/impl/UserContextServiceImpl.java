package pl.zzpj.esportbetting.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.interfaces.UserContextService;
import pl.zzpj.esportbetting.model.User;

@Service("userContextService")
public class UserContextServiceImpl implements UserContextService {

    @Override
    public User getAuthenticatedUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserDetailsImplToUserConverter.convert(userDetails);
    }
}
