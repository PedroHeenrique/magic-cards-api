package br.com.magiccards.api.player;

import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.form.NewPlayerForm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    private static NewPlayerForm newPlayerForm;

    private static Player playerAlreadyRegistered;

    @BeforeAll
    public static void buildPlayer(){

        newPlayerForm = NewPlayerForm.builder()
                .username("PlayerTest")
                .password("123456")
                .build();

        playerAlreadyRegistered = Player.builder()
                .username("PlayerTest")
                .password("123456")
                .build();

    }

    @Test
    @DisplayName("Deve registrar um novo jogador caso nao exista na base")
    public void shouldSaveNewPlayer() throws PlayerAlreadyExistException {
        when(playerService.savePlayer(newPlayerForm)).thenReturn(playerAlreadyRegistered);
        verify(playerRepository,times(1)).findByUsername(newPlayerForm.getUsername());
        Player playerSave = playerService.savePlayer(newPlayerForm);
        verify(playerRepository,times(1)).save(playerAlreadyRegistered);
        assertEquals(playerSave.getUsername(),newPlayerForm.getUsername(),"Era para executar todos os metodos de verificacao e salvar o jogador");
    }

    @Test
    @DisplayName("Deve lancar exception ao tentar salvar jogador que ja exista")
    public void shouldNotSavePlayer() throws PlayerAlreadyExistException {
        String nameNewPlayer = newPlayerForm.getUsername();
        when(playerRepository.findByUsername(nameNewPlayer)).thenReturn(Optional.of(playerAlreadyRegistered));
        PlayerAlreadyExistException ex  = assertThrows(PlayerAlreadyExistException.class,() ->
                    when(playerService.savePlayer(newPlayerForm)).thenThrow(new PlayerAlreadyExistException())
                );
        verify(playerRepository,times(0)).save(playerAlreadyRegistered);
        assertEquals("Ja existe um jogador com esse username",ex.getMessage());
    }

}
