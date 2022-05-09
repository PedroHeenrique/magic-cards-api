package br.com.magiccards.shared.exception.player;

public class PlayerInvalidException extends Exception{

    public PlayerInvalidException(){
        super("Usuário e/ ou senha inválidos");
    }
}
