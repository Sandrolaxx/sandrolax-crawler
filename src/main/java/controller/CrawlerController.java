package controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import service.CrawlerService;

@Path("/crawler")
public class CrawlerController {
    
    @Inject
    CrawlerService crawlerService;

    @GET
    public Response getImdbWorstMovies(@HeaderParam("review_star") Integer reviewStar) {
        var response = crawlerService.handleCrawl(reviewStar);

        return Response.ok(response).build();
    }

}
