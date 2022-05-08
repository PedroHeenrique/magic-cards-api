package br.com.magiccards.api.listCards;

import br.com.magiccards.shared.domain.ListCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListCardRepository extends JpaRepository<ListCards,Long> {
}
