package br.com.magiccards.shared.form;

import br.com.magiccards.shared.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewListCardsForm {
    private String username;
    private String password;
    private String listName;
    private List<Card> lisCards;
}
