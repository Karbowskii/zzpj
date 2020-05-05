package pl.zzpj.esportbetting.exception;

public class AlreadyTakenException extends ApplicationException {
    private static final String CODE = "already_taken";

    public AlreadyTakenException(String msg) {
        super(CODE, msg);
    }
}
