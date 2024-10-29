package poc.httpcache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import poc.httpcache.example.CommuneController;
import poc.httpcache.example.CommuneService;

import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static poc.httpcache.example.CommuneController.GET_ALL_COMMUNES_PATH;

@WebMvcTest(value = CommuneController.class,properties = "logging.level.org.springframework.web=debug")
class ExtensionTest {

    @Autowired
    private MockMvc mvc;

    @SpringBootApplication
    static class TestConfiguration{
        @Bean
        CommuneService communeService(){
            return new CommuneService();
        }

    }

    @Test
    void whenCallGetAllOnce_shouldReturnEtag() throws Exception {
        mvc.perform(get(GET_ALL_COMMUNES_PATH))
                .andExpect(status().isOk())
                .andExpect(header().exists("ETag"));
    }

    @Test
    void whenCallGetAllWithIfNoneMatch_shouldReturnNOtModified() throws Exception {
        var etag = new AtomicReference<String>();
        mvc.perform(get(GET_ALL_COMMUNES_PATH))
                .andExpect(status().isOk())
                .andDo(mvcResult -> etag.set(mvcResult.getResponse().getHeader("ETag")));
        mvc.perform(get(GET_ALL_COMMUNES_PATH)
                .header("If-None-Match", etag.get()))
                .andExpect(status().isNotModified());
    }


}
