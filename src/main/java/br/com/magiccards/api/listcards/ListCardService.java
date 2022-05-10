package br.com.magiccards.api.listcards;

import br.com.magiccards.api.player.PlayerService;
import br.com.magiccards.shared.domain.Card;
import br.com.magiccards.shared.domain.ListCards;
import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.exception.player.PlayerInvalidException;
import br.com.magiccards.shared.form.NewListCardsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListCardService {

    private final ListCardRepository listCardRepository;
    private final PlayerService playerService;

    @Autowired
    public ListCardService(ListCardRepository listCardRepository, PlayerService playerService) {
        this.listCardRepository = listCardRepository;
        this.playerService = playerService;
    }

    public ListCards saveListCardPlayer(NewListCardsForm newListCardsForm) throws PlayerInvalidException {
        Player playerParam = Player.builder().username(newListCardsForm.getUsername()).password(newListCardsForm.getPassword()).build();
        ListCards listCards;

        playerService.isValidPlayer(playerParam);

        Optional<Player> playerOpt = playerService.findPlayer(playerParam.getUsername());
        Player playerFound = playerOpt.get();

        listCards = ListCards.builder()
                .listName(newListCardsForm.getListName())
                .player(playerFound)
                .build();

        Set<ListCards> setOfCardsPlayer = new HashSet<>();
        setOfCardsPlayer.add(listCards);
        playerFound.setListCards(setOfCardsPlayer);

        List<Card> cards = new ArrayList<>();
        for ( Card carta: newListCardsForm.getLisCards()){
            Card card = Card.builder().build();
            card.setName(carta.getName());
            card.setEdition(carta.getEdition());
            card.setLanguage(carta.getLanguage());
            card.setFoil(carta.getFoil());
            card.setPrice(carta.getPrice());
            card.setQuantityOfThisCard(carta.getQuantityOfThisCard());
            card.setListCards(listCards);
            cards.add(card);
        }
        listCards.setCards(cards);
        listCardRepository.save(listCards);


    return listCards;
    }
}
