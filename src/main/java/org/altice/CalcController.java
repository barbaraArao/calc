package org.altice;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.altice.services.ICalcService;
import utils.ErrorMessage;

import java.math.BigInteger;

@Path("/calc")
public class CalcController {

    private static final int JSON_INPUT_LIMIT = 2_000_000;

    @Inject
    public ICalcService calcService;

    @GET
    @Path("/{n}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response calc(@PathParam("n") int n) {

        if (n <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage("Number can not be 0 or lower than 0, maximum allowed: " + JSON_INPUT_LIMIT))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (n > JSON_INPUT_LIMIT) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage("Number too large, maximum allowed: " + JSON_INPUT_LIMIT))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        BigInteger number = calcService.calc(n);
        String numberText = number.toString();

        Result result = new Result(numberText, numberText.length());
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }

    public static class Result {
        public String value;
        public int digits;

        public Result(String value, int digits) {
            this.value = value;
            this.digits = digits;
        }
    }
}
