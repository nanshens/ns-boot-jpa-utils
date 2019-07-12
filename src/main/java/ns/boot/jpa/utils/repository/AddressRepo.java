package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Address;
import ns.boot.jpa.utils.jpa.interfaces.ComplexRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends ComplexRepository<Address, String> {
}
