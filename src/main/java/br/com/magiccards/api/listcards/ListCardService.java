package br.com.magiccards.api.listcards;

import br.com.magiccards.api.player.PlayerService;
import br.com.magiccards.shared.domain.Card;
import br.com.magiccards.shared.domain.ListCard;
import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.exception.listcards.ListCardNotFoundException;
import br.com.magiccards.shared.exception.player.PlayerInvalidException;
import br.com.magiccards.shared.form.ListCardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ListCardService {

    private final ListCardRepository listCardRepository;
    private final PlayerService playerService;

    @Autowired
    public ListCardService(ListCardRepository listCardRepository, PlayerService playerService) {
        this.listCardRepository = listCardRepository;
        this.playerService = playerService;
    }

    public ListCard saveListCardPlayer(ListCardForm listCardForm) throws PlayerInvalidException {
        Player playerParam = Player.builder().username(listCardForm.getUsername()).password(listCardForm.getPassword()).build();
        ListCard listCard;

        playerService.isValidPlayer(playerParam);

        Player playerFound = playerService.findPlayer(playerParam.getUsername()).get();

        listCard = ListCard.builder()
                .listName(listCardForm.getListName())
                .player(playerFound)
                .build();

        Set<ListCard> setOfCardsPlayer = new HashSet<>();
        setOfCardsPlayer.add(listCard);
        playerFound.setListCards(setOfCardsPlayer);

        List<Card> cards = new ArrayList<>();
        for ( Card carta: listCardForm.getListCards()){
            Card card = Card.builder().build();
            card.setName(carta.getName());
            card.setEdition(carta.getEdition());
            card.setLanguage(carta.getLanguage());
            card.setFoil(carta.getFoil());
            card.setPrice(carta.getPrice());
            card.setQuantityOfThisCard(carta.getQuantityOfThisCard());
            card.setListCard(listCard);
            cards.add(card);
        }
        listCard.setCards(cards);
        listCardRepository.save(listCard);


    return listCard;
    }

    public ListCard getListCardsPlayer(Player player, String listName)throws PlayerInvalidException, ListCardNotFoundException {
        playerService.isValidPlayer(player);
        Set<ListCard> setCards = playerService.findPlayer(player.getUsername()).get().getListCards();

        return setCards.stream()
                .filter(obj -> obj.getListName().equals(listName))
                .findFirst().orElseThrow(() -> new ListCardNotFoundException(listName));

    }

    public List<ListCardNameView> getAllNamesListCards(Player player) throws ListCardNotFoundException {
        List<ListCardNameView> listCards = listCardRepository.findByPlayer_IdPlayer(player.getIdPlayer());
        if(listCards.isEmpty()){
            throw new ListCardNotFoundException("");
        }
        return  listCards;

    }
}
