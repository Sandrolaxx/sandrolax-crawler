package controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import dto.CrawlerExceptionResponseDto;
import dto.MovieDataDto;
import service.CrawlerService;

/**
 *
 * @author SRamos
 */
@Path("/crawler")
@Tag(name = "Crawler Routes")
public class CrawlerController {

    @Inject
    CrawlerService crawlerService;

    @APIResponse(responseCode = "200", description = "Caso sucesso, retorna as informações do IMDB.", content = @Content(schema = @Schema(allOf = MovieDataDto.class)))
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = CrawlerExceptionResponseDto.class)))
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImdbWorstMovies(@HeaderParam("review_star") Integer reviewStar) {
        var response = crawlerService.handleCrawl(reviewStar);

        return Response.ok(response).build();
    }

}
