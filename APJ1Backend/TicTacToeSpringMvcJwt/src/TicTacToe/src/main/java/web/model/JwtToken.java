package web.model;

import web.exception.TokenInvalidException;

public class JwtToken {
    public static String getToken(String header) throws IndexOutOfBoundsException {
        String result = "";
        try
        {
            result = header.substring(7);
        }
        catch (IndexOutOfBoundsException e) {
            throw new TokenInvalidException();
        }
        return result;
    }
}
