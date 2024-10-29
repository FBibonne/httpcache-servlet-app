package poc.httpcache;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import jakarta.servlet.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class HttpCachingFilter implements Filter {

    public static final String OAS_FILENAME = "openapi.yaml";
    private List<CachePathFilter> cachePathFilters = List.of();

    @Override
    public void init(FilterConfig filterConfig) {
        var readOas = readOAS();
        if (readOas.isPresent()){
            this.cachePathFilters = readOas.get()
                    .getPaths().entrySet().stream()
                    .map(CachePathFilter::of)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        }

    }

    private Optional<OpenAPI> readOAS() {
        return readOASFile().map(content->(new OpenAPIParser()).readContents(content, null, null))
                .map(SwaggerParseResult::getOpenAPI);
    }

    private Optional<String> readOASFile() {
        try(var lines = Files.lines(Path.of(HttpCachingFilter.class.getClassLoader().getResource(oasFilename()).toURI()))) {
            return of(lines.collect(Collectors.joining()));
        } catch (IOException | URISyntaxException e) {
            return empty();
        }
    }

    private static String oasFilename() {
        return OAS_FILENAME;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var path = request.getServletContext().getContextPath();
        var cachePathFilter = cachePathFilters.stream().filter(filter -> filter.match(path)).findFirst();
        if (cachePathFilter.isPresent()) {
            cachePathFilter.get().doFilter(request, response, chain);
        }
        chain.doFilter(request, response);
    }
}
