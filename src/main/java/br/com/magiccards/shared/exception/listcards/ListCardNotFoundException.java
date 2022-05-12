package br.com.magiccards.shared.exception.listcards;

public class ListCardNotFoundException extends Exception{

    public ListCardNotFoundException(String listName){
        super("Lista(s) nao encontrada(s) " + listName);
    }

}
