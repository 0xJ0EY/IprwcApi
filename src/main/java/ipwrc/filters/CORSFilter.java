package ipwrc.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CORSFilter implements ContainerResponseFilter {

    /**
     * Enable CORS so the client can connect from everywhere
     * More about CORS: https://en.wikipedia.org/wiki/Cross-origin_resource_sharing
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response) throws IOException {

        // Setup origin
        response.getHeaders().add("Access-Control-Allow-Origin", "*");

        // Setup headers
        response.getHeaders().add("Access-Control-Allow-Headers",
                "X-Requested-With,Content-Type,Accept,Authorization,Origin");

        // Setup allowed methods
        response.getHeaders().add("Access-Control-Allow-Methods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

    }
}
