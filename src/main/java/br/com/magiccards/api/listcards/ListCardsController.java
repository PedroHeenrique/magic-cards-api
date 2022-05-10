package br.com.magiccards.api.listcards;

import br.com.magiccards.shared.domain.ListCards;
import br.com.magiccards.shared.exception.player.PlayerInvalidException;
import br.com.magiccards.shared.form.NewListCardsForm;
import br.com.magiccards.shared.swagger.SwaggerConfigurations;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/list-card")
@Api(tags = {SwaggerConfigurations.LIST_CARDS})
public class ListCardsController {

    private final ListCardService listCardService;

    @Autowired
    public ListCardsController(ListCardService listCardService) {
        this.listCardService = listCardService;
    }
    @ApiOperation(value ="Criação de listas de cartas")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista criada com sucesso"),
            @ApiResponse(code = 400, message = "valores informados invalidos" ),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ListCards> saveNewListCard(@RequestBody @Valid NewListCardsForm newListCards)throws PlayerInvalidException{
        ListCards listCardsCreated = listCardService.saveListCardPlayer(newListCards);
        return ResponseEntity.status(HttpStatus.CREATED).body(listCardsCreated);
    }
}
