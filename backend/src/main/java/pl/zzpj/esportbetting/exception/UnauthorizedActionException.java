package pl.zzpj.esportbetting.exception;

import org.apache.commons.lang3.StringUtils;

public class UnauthorizedActionException extends ApplicationException {
    private static final String CODE = "unauthorized_action";

    public UnauthorizedActionException() {
        super(CODE, StringUtils.EMPTY);
    }

    public UnauthorizedActionException(String msg) {
        super(CODE, msg);
    }
}
