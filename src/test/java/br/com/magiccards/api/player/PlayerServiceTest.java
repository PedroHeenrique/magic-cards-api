package br.com.magiccards.api.player;

import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.exception.player.PlayerAlreadyExistException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepositoryMock;

    private static Player playerToSave;

    @BeforeAll
    public static void buildPlayer(){
        playerToSave = Player.builder()
                .username("PlayerTest")
                .password("123456")
                .build();
    }

    @Test
    @DisplayName("Deve registrar um novo jogador caso nao exista na base")
    public void shouldSaveNewPlayer() throws PlayerAlreadyExistException {
       doReturn(playerToSave).when(playerRepositoryMock).save(playerToSave);
       String passwordBeforeSaving = playerToSave.getPassword();

       Player player = playerService.savePlayer(playerToSave);
       verify(playerRepositoryMock).findByUsername(playerToSave.getUsername());
       boolean passwordEncrypted = !passwordBeforeSaving.equals(player.getPassword());
       boolean areTheSame = Objects.equals(playerToSave.getUsername(), player.getUsername());

       assertTrue(areTheSame,"Os objetos eram para ser os mesmos");
       assertTrue(passwordEncrypted,"Senha e para ter sido encriptada");
    }

    @Test
    @DisplayName("Deve lancar exception ao tentar salvar jogador que ja exista")
    public void shouldNotSavePlayer() {

        String nameNewPlayer = playerToSave.getUsername();
        when(playerRepositoryMock.findByUsername(nameNewPlayer)).thenReturn(Optional.of(playerToSave));
        PlayerAlreadyExistException ex  = assertThrows(PlayerAlreadyExistException.class,() ->
                    when(playerService.savePlayer(playerToSave)).thenThrow(new PlayerAlreadyExistException())
                );
        verify(playerRepositoryMock,times(0)).save(playerToSave);
        assertEquals("Ja existe um jogador com esse username",ex.getMessage());

    }

}
