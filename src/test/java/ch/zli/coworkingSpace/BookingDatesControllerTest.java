package ch.zli.coworkingSpace;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.security.JwtServiceHMAC;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingDatesControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @LocalServerPort
    private int port;

    @Autowired
    private JwtServiceHMAC jwtService;

    private String accessToken;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    public void init() {
        accessToken = jwtService.createNewJWT(UUID.randomUUID().toString(), "1", "email@gmail.com", List.of("ADMIN"));
    }

    BookingDatesEntity bookEntity1 = new BookingDatesEntity();

    BookingDatesEntity bookEntity2 = new BookingDatesEntity();

    List<BookingDatesEntity> bookEntityList = new ArrayList<>();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    HttpHeaders headers = new HttpHeaders();

    @Before("")
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        bookEntity1.setId(Long.valueOf(1));
        bookEntity1.setBooked(false);
        bookEntity1.setDate(new Date(12-14-2022));
        bookEntity1.setUser_id(1);
        bookEntity1.setPrice(120);
        bookEntity1.setWholeDay(true);
        bookEntityList.add(bookEntity1);
        bookEntityList.add(bookEntity2);
    }

    @Test  //@GET("/bookings")
    public void givenBookEntity_whenGetAllBook_thenStatus200()
            throws Exception {

        mockMvc.perform(get("/bookings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test //@GET("/bookings/{id}")
    public void givenBookDTO_whenCallingControllerFindIdBy_thenStatus200()
            throws Exception {
        HttpEntity<BookingDatesEntity> entity = new HttpEntity<BookingDatesEntity>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/bookings/1"),
                HttpMethod.GET, entity, String.class);

        String expected = "{\"id\":1,\"date\":\"12-14-2022\",\"whole_day\":\"true\",\"booked\":\"true\", \"user_id\":\"1\", \"price\":\"120\"}";

        Assertions.assertEquals(expected, String.valueOf(response.getBody()));
    }

}