package br.com.magiccards.api.cards;

import br.com.magiccards.shared.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<Card,Long> {
}
