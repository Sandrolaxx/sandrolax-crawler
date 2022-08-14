package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import dto.CrawlerExceptionResponseDto;

/**
 *
 * @author SRamos
 */
@Provider
public class ErrorResponseExceptionMapper implements ExceptionMapper<CrawlerException> {

    @Override
    public Response toResponse(CrawlerException ex) {
        var formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        var exceptionResponse = new CrawlerExceptionResponseDto();

        exceptionResponse.setError(ex.getError());
        exceptionResponse.setDate(formattedDate);

        return Response.status(ex.getHttpStatus()).entity(exceptionResponse).build();
    }

}