package controllers;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import services.CrawlerService;

@Path("/crawler")
public class CrawlerController {
    
    @Inject
    CrawlerService crawlerService;

    @GET
    public Response getImdbWorstMovies() {
        var response = crawlerService.handleCrawl();

        return Response.ok(response).build();
    }

}
