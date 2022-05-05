package br.com.magiccards.api.player;
import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.dto.NewPlayerForm;
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
public class PlayerController {
    private final PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Player> registerNewPlayer(@RequestBody @Valid NewPlayerForm  newPlayerForm) throws PlayerAlreadyExistException {
        try{
             Player playerSave = playerService.savePlayer(newPlayerForm);
            return ResponseEntity.status(HttpStatus.CREATED).body(playerSave);
        }catch (PlayerAlreadyExistException ex){
            throw new PlayerAlreadyExistException();
        }

    }
}
