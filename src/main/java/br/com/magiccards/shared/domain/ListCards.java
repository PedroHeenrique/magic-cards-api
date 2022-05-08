package br.com.magiccards.shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "list_cards")
@JsonIgnoreProperties({"idListCard","player"})
public class ListCards implements Serializable {

    private static final long serialVersionUID = -1798070786793154676L;
    @Id
    @Column(name = "id_list_card")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idListCard;
    @Column(name = "list_name", length =20)
    private String listName;
    @ManyToOne
    @JoinColumn(name = "id_player", nullable = false)
    private Player player;
    @OneToMany(mappedBy = "listCards", cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    public Long getIdListCard() {
        return idListCard;
    }

    public void setIdListCard(Long idListCard) {
        this.idListCard = idListCard;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListCards listCards = (ListCards) o;
        return Objects.equals(getIdListCard(), listCards.getIdListCard()) && Objects.equals(getListName(), listCards.getListName()) && Objects.equals(getPlayer(), listCards.getPlayer()) && Objects.equals(getCards(), listCards.getCards());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdListCard(), getListName(), getPlayer(), getCards());
    }
}
