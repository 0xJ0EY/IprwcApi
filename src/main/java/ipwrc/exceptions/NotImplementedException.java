package ipwrc.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotImplementedException extends WebApplicationException {

    public NotImplementedException() {
        super("Method is not implemented", Response.Status.INTERNAL_SERVER_ERROR);
    }
}
