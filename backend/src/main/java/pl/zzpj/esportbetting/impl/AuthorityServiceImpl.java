package pl.zzpj.esportbetting.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.exception.ObjectNotFoundException;
import pl.zzpj.esportbetting.interfaces.AuthorityService;
import pl.zzpj.esportbetting.model.Authority;
import pl.zzpj.esportbetting.repository.AuthorityRepository;

@Service("authorityService")
class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority findByName(AuthorityEnum name) {
        return authorityRepository.findByName(name).orElseThrow(
                () -> new ObjectNotFoundException("Not found authority with name:" + name));
    }

}
