package br.com.magiccards.shared.exception.player;

public class PlayerAlreadyExistException extends Exception{
    public PlayerAlreadyExistException(){
        super("Ja existe um jogador com esse username");
    }
}
