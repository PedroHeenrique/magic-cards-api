package br.com.magiccards.api.player;

import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.form.NewPlayerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }
    public Player savePlayer(NewPlayerForm player) throws PlayerAlreadyExistException {
        Optional<Player> optionalPlayer = playerRepository.findByUsername(player.getUsername());

        if(optionalPlayer.isPresent()){
            throw new PlayerAlreadyExistException();
        }
        Player newPlayer = Player.builder()
                                 .username(player.getUsername())
                                 .password(player.getPassword())
                                 .build();
        return playerRepository.save(newPlayer);
    }


}
