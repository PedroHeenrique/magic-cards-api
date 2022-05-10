package br.com.magiccards.api.listcards;

import br.com.magiccards.shared.domain.ListCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListCardRepository extends JpaRepository<ListCards,Long> {
}
