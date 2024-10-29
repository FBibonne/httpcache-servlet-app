package poc.httpcache.example;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import poc.httpcache.annotation.HttpCache;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommuneController {

    public static final String GET_ALL_COMMUNES_PATH = "/communes";
    private final CommuneService communeService;

    @GetExchange(GET_ALL_COMMUNES_PATH)
    @HttpCache
    public List<Commune> getAllCommunes(){
        return communeService.getAllCommunes();
    }

}
