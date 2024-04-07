package nl.ns.iris.entertain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ServiceApplicationTest {

    @MockBean
    private RestClient.Builder restClientBuilder;
    @MockBean
    private RestClient restClient;

    @Test
    public void main_startsSpringApplication() {

        when(restClientBuilder.build()).thenReturn(restClient);
        
        // if spring context loads without any exceptions test is considered passed
        SpringApplication.run(ServiceApplication.class);
    }
}