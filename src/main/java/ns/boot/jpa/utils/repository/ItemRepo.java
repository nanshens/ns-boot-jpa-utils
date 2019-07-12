package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Item;
import ns.boot.jpa.utils.jpa.interfaces.ComplexRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends ComplexRepository<Item, String> {
}
