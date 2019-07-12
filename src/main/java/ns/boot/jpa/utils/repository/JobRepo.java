package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends JpaRepository<Job, String>, JpaSpecificationExecutor<Job> {
}
