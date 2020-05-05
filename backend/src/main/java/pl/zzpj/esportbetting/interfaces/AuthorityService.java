package pl.zzpj.esportbetting.interfaces;

import pl.zzpj.esportbetting.enumerate.AuthorityEnum;
import pl.zzpj.esportbetting.model.Authority;

public interface AuthorityService {

    Authority findByName(AuthorityEnum authorityEnum);
}
