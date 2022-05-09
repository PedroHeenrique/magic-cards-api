package br.com.magiccards.api.player;

import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.exception.player.PlayerAlreadyExistException;
import br.com.magiccards.shared.exception.player.PlayerInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public Player savePlayer(Player player) throws PlayerAlreadyExistException {
        Optional<Player> optionalPlayer = playerRepository.findByUsername(player.getUsername());

        if(optionalPlayer.isPresent()){
            throw new PlayerAlreadyExistException();
        }

        String passwordEncoded = encodePassword(player.getPassword());
        player.setPassword(passwordEncoded);
        return playerRepository.save(player);
    }

    public void isValidPlayer(Player player) throws PlayerInvalidException {
        Optional<Player> optPlayer = findPlayer(player.getUsername());
        String passwordEntered = player.getPassword();

        if(optPlayer.isPresent()){
           String passwordSave = optPlayer.get().getPassword();
           if(decodePassword(passwordEntered,passwordSave)){
               return;
           }
        }

        throw new PlayerInvalidException();
    }

    public Optional<Player> findPlayer(String username){
        return playerRepository.findByUsername(username);
    }

    private String encodePassword(String password){
       return new BCryptPasswordEncoder().encode(password);
    }

    private Boolean decodePassword(String passwordEntered, String passwordSave){
        return new BCryptPasswordEncoder().matches(passwordEntered,passwordSave);
    }




}
