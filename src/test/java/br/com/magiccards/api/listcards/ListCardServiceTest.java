package br.com.magiccards.api.listcards;

import br.com.magiccards.api.player.PlayerService;
import br.com.magiccards.shared.domain.ListCard;
import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.exception.player.PlayerInvalidException;
import br.com.magiccards.shared.form.ListCardForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListCardServiceTest {

    @InjectMocks
    private ListCardService listCardService;

    @Mock
    private PlayerService playerService;

    @Mock
    private ListCardRepository listCardRepository;

    private static final String invalidLanguageJsonNewListCard =
            "{\n" +
                    "    \"username\": \"teste\",\n" +
                    "    \"password\": \"123456\",\n" +
                    "    \"listName\": \"WarriorsCards\",\n" +
                    "    \"listCards\": [\n" +
                    "        {\n" +
                    "            \"name\": \"Pantheon\",\n" +
                    "            \"edition\": 1,\n" +
                    "            \"language\": \"pt\",\n" +
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

    private static final String jsonNewListCardSave =
            "{\n" +
                    "    \"username\": \"tes\",\n" +
                    "    \"password\": \"123456\",\n" +
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

    private static Player playerOwnerListCard;
    private static ListCardForm listCardForm;
    private static ListCardForm listCardFormInvalidPlayer;

    private static Optional<Player> playerFound;

    private static ListCard listCardSave;

    private static ListCard listCardSaveDataBase;

    @BeforeAll
    public static void buildListCard() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        listCardFormInvalidPlayer = mapper.readerFor(ListCardForm.class).readValue(invalidLanguageJsonNewListCard);
        listCardForm = mapper.readerFor(ListCardForm.class).readValue(jsonNewListCardSave);
        listCardSave = mapper.readerFor(ListCard.class).readValue(jsonListCardSaveReturn);

        playerOwnerListCard = Player.builder()
                                    .username(listCardForm.getUsername())
                                    .password(listCardForm.getPassword())
                                    .build();

        playerFound = Optional.of(Player.builder()
                .username(playerOwnerListCard.getUsername())
                .password(playerOwnerListCard.getPassword())
                .idPlayer(1L)
                .build());

        listCardSaveDataBase = ListCard.builder()
                                        .listName(listCardForm.getListName())
                                        .cards(listCardSave.getCards())
                                        .player(playerOwnerListCard)
                                        .build();

    }

    @Test
    @DisplayName("Deve salvar a lista de cartas para um jogador valido")
    public void shouldSaveNewListPlayerValid() throws PlayerInvalidException {
        doReturn(playerFound).when(playerService).findPlayer(playerOwnerListCard.getUsername());
        doReturn(listCardSave).when(listCardRepository).save(listCardSaveDataBase);

        ListCard listCard = listCardService.saveListCardPlayer(listCardForm);
        verify(playerService).isValidPlayer(playerOwnerListCard);
        assertEquals(listCard.getCards().size(), listCardForm.getListCards().size());
    }

    @Test
    @DisplayName("Deve lançar  exception ao tentar salvar uma lista com jogador invalido")
    public void shouldNotSaveLisCardPlayerInvalid(){
        PlayerInvalidException ex = assertThrows(PlayerInvalidException.class, () ->{
                doThrow(new PlayerInvalidException()).when(playerService).isValidPlayer(any(Player.class));
            listCardService.saveListCardPlayer(listCardFormInvalidPlayer);
        });
        verify(listCardRepository,times(0)).save(listCardSaveDataBase);
    }


}
