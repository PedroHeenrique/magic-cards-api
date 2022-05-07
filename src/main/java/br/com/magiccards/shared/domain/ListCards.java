package br.com.magiccards.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "list_cards")
public class ListCards {
    @Id
    @Column(name = "id_list_card")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idListCard;
    @ManyToOne
    @JoinColumn(name = "id_player", nullable = false)
    private Player player;

    @OneToMany(mappedBy = "listCards")
    private List<Card> card;



}
