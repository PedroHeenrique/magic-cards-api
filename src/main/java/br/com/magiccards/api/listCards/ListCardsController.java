package br.com.magiccards.api.listCards;

import br.com.magiccards.shared.form.NewListCardsForm;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean saveNewListCard(@RequestBody @Valid NewListCardsForm newListCards){
        listCardService.saveListCardPlayer(newListCards);
      return true;
    }
}
