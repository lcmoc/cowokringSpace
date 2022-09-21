package ch.zli.coworkingSpace;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.repository.BookingDatesRepository;
import ch.zli.coworkingSpace.repository.UserRepository;
import ch.zli.coworkingSpace.security.JwtServiceHMAC;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Description;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingDatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtServiceHMAC jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingDatesRepository bookingDates;

    @Autowired
    private UserRepository userRepository;

    private String accessToken;

    @BeforeEach
    public void init() {
        accessToken = jwtService.createNewJWT(UUID.randomUUID().toString(), "13", "Liselotte.Muster@mail.com", List.of("ADMIN"));
    }

    @Test
    @Description("Tests if all entries in the inital db are reachable")
    public void allBookingsShouldBeReturnedFromService() throws Exception {
        val response = mockMvc.perform(get("/bookings").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        List<BookingDatesEntity> bookings = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(4, bookings.size());
    }


}