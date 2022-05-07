package br.com.magiccards.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    @Positive(message = "informe uma edição valida")
    private Integer edition;
    @NotBlank(message = "informe a sigla de um idioma dentre essas 3 - PT(português) EN(inglês) JA(japonês)")
    @Size(min = 2, max = 2, message = "idioma invalido considere uma sigla dentre as 3 - PT(português) EN(inglês) JA(japonês)")
    @Pattern(regexp = "^[pP][tT]|[eE][nN]|[jJ][aA]$", message = "linguagem invalida considere uma sigla dentre as 3 - PT(português) EN(inglês) JA(japonês)")
    private String language;
    @Column(name = "is_foil")
    private Boolean isFoil;
    @Positive
    private Double price;
    @Positive
    @Column(name = "quantity_of_this_card")
    private Long quantityOfThisCard;

    @ManyToOne
    @JoinColumn(name = "id_list_card", nullable = false)
    private ListCards listCards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getFoil() {
        return isFoil;
    }

    public void setFoil(Boolean foil) {
        isFoil = foil;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantityOfThisCard() {
        return quantityOfThisCard;
    }

    public void setQuantityOfThisCard(Long quantityOfThisCard) {
        this.quantityOfThisCard = quantityOfThisCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getName().equals(card.getName()) && getEdition().equals(card.getEdition()) &&
                getLanguage().equals(card.getLanguage()) && getFoil().equals(card.getFoil()) &&
                getPrice().equals(card.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEdition(), getLanguage(), getFoil(), getPrice());
    }
}
