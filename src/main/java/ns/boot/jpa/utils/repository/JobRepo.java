package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Job;
import ns.boot.jpa.utils.jpa.interfaces.ComplexRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends ComplexRepository<Job, String> {
}
