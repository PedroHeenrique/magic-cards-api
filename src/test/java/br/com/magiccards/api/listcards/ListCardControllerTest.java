package br.com.magiccards.api.listcards;

import br.com.magiccards.shared.domain.ListCard;
import br.com.magiccards.shared.form.ListCardForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ListCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListCardService listCardService;

    private static final String invalidLanguageJsonNewListCard =
            "{\n" +
            "    \"username\": \"teste\",\n" +
            "    \"password\": \"123456\",\n" +
            "    \"listName\": \"WarriorsCards\",\n" +
            "    \"listCards\": [\n" +
            "        {\n" +
            "            \"name\": \"Pantheon\",\n" +
            "            \"edition\": 1,\n" +
            "            \"language\": \"fr\",\n" +
            "            \"price\": 85.0,\n" +
            "            \"quantityOfThisCard\": 1,\n" +
            "            \"foil\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"Garen\",\n" +
            "            \"edition\": 1,\n" +
            "            \"language\": \"ca\",\n" +
            "            \"price\": 45.0,\n" +
            "            \"quantityOfThisCard\": 1,\n" +
            "            \"foil\": false\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static final String jsonNewListCardRegister =
            "{\n" +
            "    \"username\": \"teste\",\n" +
            "    \"password\": \"454556\",\n" +
            "    \"listName\": \"WarriorsCards\"," +
            "    \"listCards\": [\n" +
            "        {\n" +
            "            \"name\": \"Pantheon\"," +
            "            \"edition\": 1,\n" +
            "            \"language\": \"ja\"," +
            "            \"price\": 85.0,\n" +
            "            \"quantityOfThisCard\": 1," +
            "            \"foil\": true" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"Garen\"," +
            "            \"edition\": 1," +
            "            \"language\": \"en\"," +
            "            \"price\": 45.0,\n" +
            "            \"quantityOfThisCard\": 1," +
            "            \"foil\": false" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static final String jsonListCardSaveReturn =
            "{\n" +
            "    \"listName\": \"WarriorsCards\",\n" +
            "    \"cards\": [\n" +
            "        {\n" +
            "            \"name\": \"Pantheon\",\n" +
            "            \"edition\": 1,\n" +
            "            \"language\": \"ja\",\n" +
            "            \"price\": 85.0,\n" +
            "            \"quantityOfThisCard\": 1,\n" +
            "            \"foil\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"Garen\",\n" +
            "            \"edition\": 1,\n" +
            "            \"language\": \"en\",\n" +
            "            \"price\": 45.0,\n" +
            "            \"quantityOfThisCard\": 1,\n" +
            "            \"foil\": false\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    private static ListCardForm listCardsForm;
    private static ListCard listCardReturn;

    @BeforeAll
    public static void buildListCard() throws JsonProcessingException {
        listCardsForm = new ObjectMapper().readerFor(ListCardForm.class).readValue(jsonNewListCardRegister);
        listCardReturn = new ObjectMapper().readerFor(ListCard.class).readValue(jsonListCardSaveReturn);
    }

    @Test
    @DisplayName("Deve salvar lista de cartas de um jogador valido")
    public void shouldReturn201WhenSaveLisCards()throws Exception {
       when(listCardService.saveListCardPlayer(listCardsForm)).thenReturn(listCardReturn);
       mockMvc.perform(post("/list-cards")
               .contentType(MediaType.APPLICATION_JSON)
               .content(jsonNewListCardRegister)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.listName").value("WarriorsCards"))
               .andExpect(jsonPath("$.cards[0].name").value("Pantheon"))
               .andExpect(jsonPath("$.cards[0].language").value("ja"))
               .andExpect(jsonPath("$.cards[0].foil").value(true))
               .andExpect(jsonPath("$.cards[1].name").value("Garen"))
               .andExpect(jsonPath("$.cards[1].language").value("en"))
               .andExpect(jsonPath("$.cards[1].foil").value(false));

    }

    @Test
    @DisplayName("Deve gerar erro por ser informado um json invalido")
    public void shouldReturn400WhenSaveListCardsWithInvalidProperties() throws Exception {
        mockMvc.perform(post("/list-cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidLanguageJsonNewListCard)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }



}
