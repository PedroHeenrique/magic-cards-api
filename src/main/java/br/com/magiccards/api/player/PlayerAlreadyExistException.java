package br.com.magiccards.api.player;

public class PlayerAlreadyExistException extends Exception{
    public PlayerAlreadyExistException(){
        super("Ja existe um jogador com esse username");
    }
}
