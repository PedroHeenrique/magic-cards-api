package br.com.magiccards.api.listcards;

import br.com.magiccards.shared.domain.ListCards;
import br.com.magiccards.shared.exception.player.PlayerInvalidException;
import br.com.magiccards.shared.form.NewListCardsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/list-card")
public class ListCardsController {

    private final ListCardService listCardService;

    @Autowired
    public ListCardsController(ListCardService listCardService) {
        this.listCardService = listCardService;
    }

    @PostMapping
    public ResponseEntity<ListCards> saveNewListCard(@RequestBody @Valid NewListCardsForm newListCards)throws PlayerInvalidException{
        ListCards listCardsCreated = listCardService.saveListCardPlayer(newListCards);
        return ResponseEntity.status(HttpStatus.CREATED).body(listCardsCreated);
    }
}
