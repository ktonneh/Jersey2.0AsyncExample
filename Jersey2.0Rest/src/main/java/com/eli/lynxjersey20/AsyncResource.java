/**
 * 
 */
package com.eli.lynxjersey20;

/**
 * @author Elly
 *
 */
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.Response;
 
@Path("/resource")
public class AsyncResource {

    @GET
    @Path("/testasync")
    public Response testAsync(){

        return Response.status(200).entity("Async Api Is up").build();
    }

    /*
        Perform Async Background Task
     */
      @GET
      @Consumes()
      @Produces()
      @Path("/timeoutAsync")
      public void asyncGetWithTimeout(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {
          @Override
          public void handleTimeout(AsyncResponse asyncResponse) {
            asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation time out.").build());
          }
        });

        asyncResponse.setTimeout(5, TimeUnit.SECONDS);
        new Thread(new Runnable() {
          @Override
          public void run() {
            String result = veryExpensiveOperation();



            asyncResponse.resume(result);
          }
          private String veryExpensiveOperation() {

              return "Very Expensive Operation with Timeout";
          }
        }).start();
      }




    /*
    Perform Async Validation Task
 */
    @GET
    @Consumes()
    @Produces()
    @Path("/timeoutAsync")
    public void validateAfis(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {
            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation time out.").build());
            }
        });

        asyncResponse.setTimeout(5, TimeUnit.SECONDS);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = veryExpensiveOperation();



                asyncResponse.resume(result);
            }
            private String veryExpensiveOperation() {

                return "Very Expensive Operation with Timeout";
            }
        }).start();
    }


 
}