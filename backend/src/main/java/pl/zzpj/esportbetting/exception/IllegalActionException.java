package pl.zzpj.esportbetting.exception;

public class IllegalActionException extends ApplicationException {
    private static final String CODE = "illegal_action";

    public IllegalActionException(String msg) {
        super(CODE, msg);
    }
}
