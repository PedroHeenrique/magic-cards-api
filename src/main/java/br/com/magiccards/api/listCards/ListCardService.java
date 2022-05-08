package br.com.magiccards.api.listCards;

import br.com.magiccards.api.player.PlayerRepository;
import br.com.magiccards.shared.domain.Card;
import br.com.magiccards.shared.domain.ListCards;
import br.com.magiccards.shared.domain.Player;
import br.com.magiccards.shared.form.NewListCardsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ListCardService {

    private final ListCardRepository listCardRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public ListCardService(ListCardRepository listCardRepository, PlayerRepository playerRepository) {
        this.listCardRepository = listCardRepository;
        this.playerRepository = playerRepository;
    }

    public boolean saveListCardPlayer(NewListCardsForm newListCardsForm){
        Optional<Player> player = playerRepository.findByUsername(newListCardsForm.getUsername());

        Player player1 = Player.builder().build();
        if(player.isPresent()){
            player1 = player.get();
            ListCards listaCards = ListCards.builder()
                    .listName(newListCardsForm.getListName())
                    .player(player1)
                    .build();

            Set<ListCards> conjunto = new HashSet<>();
            conjunto.add(listaCards);
            player1.setListCards(conjunto);

            List<Card> cartas = new ArrayList<>();
            for ( Card carta: newListCardsForm.getLisCards()){
                Card card = Card.builder().build();
                card.setName(carta.getName());
                card.setEdition(carta.getEdition());
                card.setLanguage(carta.getLanguage());
                card.setFoil(carta.getFoil());
                card.setPrice(carta.getPrice());
                card.setQuantityOfThisCard(carta.getQuantityOfThisCard());
                card.setListCards(listaCards);
                cartas.add(card);
            }

            listaCards.setCards(cartas);
        }
        playerRepository.save(player1);
        return true;
    }
}
