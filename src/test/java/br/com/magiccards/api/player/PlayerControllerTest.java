package br.com.magiccards.api.player;

import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.dto.NewPlayerForm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  PlayerService playerService;

    private final String jsonPlayerRegister = "{\n" +
            "    \"username\":\"UserTest\",\n" +
            "    \"password\":\"123456\"\n" +
            "}";

    private static Player playerAlreadyRegistered;

    @BeforeAll
    private static void buildNewPlayer(){
        playerAlreadyRegistered = Player.builder()
                .username("PlayerTest")
                .password("123456")
                .build();

    }

    @Test
    public void shouldReturn201WhenSaveNewPlayer() throws Exception {
        when(playerService.savePlayer(any(NewPlayerForm.class))).thenReturn(playerAlreadyRegistered);
        mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPlayerRegister)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("PlayerTest"));

    }
}
