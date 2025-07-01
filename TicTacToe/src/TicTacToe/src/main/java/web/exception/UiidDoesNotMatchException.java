package web.exception;

public class UiidDoesNotMatchException extends RuntimeException {
    public UiidDoesNotMatchException(int uuid_path, int uuid_body) {
        super("UUID in the path " + uuid_path +  " does not match with uuid in the body " + uuid_body);
    }
}
