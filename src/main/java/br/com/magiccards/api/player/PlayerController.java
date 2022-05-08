package br.com.magiccards.api.player;
import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.exception.player.PlayerAlreadyExistException;
import br.com.magiccards.shared.form.NewPlayerForm;
import br.com.magiccards.shared.swagger.SwaggerConfigurations;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/player")
@Api(tags = {SwaggerConfigurations.PLAYER_TAG})
public class PlayerController {
    private final PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @ApiOperation(value = "Registra um novo jogador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Jogador registrado com sucesso"),
            @ApiResponse(code = 400, message = "Valores inválido  ou jogador ja cadastrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @PostMapping(consumes = "application/json",produces = "application/json")
    public ResponseEntity<Player> registerNewPlayer(@ApiParam(value = "email e senha",required = true)
            @RequestBody @Valid NewPlayerForm  newPlayerForm) throws PlayerAlreadyExistException {
        try{
             Player playerToSave = Player.builder().username(newPlayerForm.getUsername()).password(newPlayerForm.getPassword()).build();
             Player playerSave = playerService.savePlayer(playerToSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(playerSave);
        }catch (PlayerAlreadyExistException ex){
            throw new PlayerAlreadyExistException();
        }

    }
}
