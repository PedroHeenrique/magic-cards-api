package br.com.magiccards.api.listcards;

import br.com.magiccards.shared.domain.ListCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListCardRepository extends JpaRepository<ListCard,Long> {
}
