package br.com.magiccards.shared.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPlayerForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
