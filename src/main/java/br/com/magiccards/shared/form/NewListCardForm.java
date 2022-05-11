package br.com.magiccards.shared.form;

import br.com.magiccards.shared.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewListCardForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String listName;
    @NotEmpty
    private List<@Valid Card> listCards;
}
