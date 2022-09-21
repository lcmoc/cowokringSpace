package ch.zli.coworkingSpace;

import ch.zli.coworkingSpace.model.BookingDatesEntity;
import ch.zli.coworkingSpace.model.UserEntity;
import ch.zli.coworkingSpace.repository.UserRepository;
import ch.zli.coworkingSpace.security.JwtServiceHMAC;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Description;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtServiceHMAC jwtService;

    @Autowired
    private ObjectMapper objectMapper;

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
        val response = mockMvc.perform(get("/users").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        List<UserEntity> users = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(5, users.size());
    }
}
