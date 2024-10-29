package poc.httpcache;

import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.Map;
import java.util.Optional;

record CacheResponse(String httpResponseCode, Object cacheExtension) {
    public static Optional<CacheResponse> of(Map.Entry<String, ApiResponse> entry) {
        var cacheExtension = entry.getValue().getExtensions().get("x-cache");
        if (cacheExtension != null) {
            return Optional.of(new CacheResponse(entry.getKey(), cacheExtension));
        }
        return Optional.empty();
    }
}
