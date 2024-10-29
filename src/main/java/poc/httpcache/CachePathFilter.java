package poc.httpcache;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

record CachePathFilter(String path, List<CacheResponse> cacheResponses) {

    public static Optional<CachePathFilter> of(Map.Entry<String, PathItem> entry) {
        Operation get = entry.getValue().getGet();
        if (get != null){
            var cacheResponses = findCacheExtensions(get);
            if (! cacheResponses.isEmpty()){
                return Optional.of(new CachePathFilter(entry.getKey(), cacheResponses));
            }
        }
        return Optional.empty();
    }

    private static List<CacheResponse> findCacheExtensions(Operation operation) {
        return operation.getResponses().entrySet().stream()
                .map(CacheResponse::of)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public boolean match(String path) {
       return this.path.equals(path);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String eTag=null;
        boolean matchRequest = isMatchRequest(request);
        if (matchRequest){
            eTag = readEtag(request);
        }
        chain.doFilter(request, response);
        String newETag = computeEtag(response);
        if (matchRequest && newETag.equals(eTag)){
            response = notModifiedResponse(eTag);
        }
        response = addEtagHeader(newETag, response);
    }

}
