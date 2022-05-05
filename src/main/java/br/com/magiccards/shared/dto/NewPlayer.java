package br.com.magiccards.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPlayer {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
