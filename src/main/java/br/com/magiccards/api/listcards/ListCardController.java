package br.com.magiccards.api.listcards;

import br.com.magiccards.api.player.PlayerService;
import br.com.magiccards.shared.domain.ListCard;
import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.exception.listcards.ListCardNotFoundException;
import br.com.magiccards.shared.exception.player.PlayerInvalidException;
import br.com.magiccards.shared.form.ListCardForm;
import br.com.magiccards.shared.form.PlayerForm;
import br.com.magiccards.shared.swagger.SwaggerConfigurations;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/list-cards")
@Api(tags = {SwaggerConfigurations.LIST_CARDS})
public class ListCardController {

    private final ListCardService listCardService;

    private final PlayerService playerService;

    @Autowired
    public ListCardController(ListCardService listCardService, PlayerService playerService) {
        this.listCardService = listCardService;
        this.playerService = playerService;
    }
    @ApiOperation(value ="Criação de lista de cartas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista criada com sucesso"),
            @ApiResponse(code = 400, message = "valores informados inválidos" ),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ListCard> saveNewListCard(@RequestBody @Valid ListCardForm newListCards)throws PlayerInvalidException{
        ListCard listCardCreated = listCardService.saveListCardPlayer(newListCards);
        return ResponseEntity.status(HttpStatus.CREATED).body(listCardCreated);
    }

    @ApiOperation(value ="Obter uma lista de cartas especifica de um jogador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista obtida com sucesso"),
            @ApiResponse(code = 400, message = "valores informados inválidos" ),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @GetMapping(value = "{listName}",consumes = "application/json", produces = "application/json")
    public ResponseEntity<ListCard> getListCards(@RequestBody @Valid PlayerForm playerForm, @PathVariable String listName)throws PlayerInvalidException, ListCardNotFoundException {
        Player player = Player.builder().username(playerForm.getUsername()).password(playerForm.getPassword()).build();
        ListCard listCards = listCardService.getListCardsPlayer(player, listName);
        return ResponseEntity.status(HttpStatus.OK).body(listCards);
    }

    @ApiOperation(value ="Obter nomes das listas de cartas de um jogador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Listas obtidas com sucesso"),
            @ApiResponse(code = 400, message = "valores informados inválidos"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @GetMapping("/me")
    public List<ListCardNameView> getMeListCardsName(@RequestBody @Valid PlayerForm playerForm) throws ListCardNotFoundException {
        Player playerParam = playerService.findPlayer(playerForm.getUsername()).get();
        return listCardService.getAllNamesListCards(playerParam);
    }
}
