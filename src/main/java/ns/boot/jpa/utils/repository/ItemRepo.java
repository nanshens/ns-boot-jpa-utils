package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, String>, JpaSpecificationExecutor<Item> {
}
