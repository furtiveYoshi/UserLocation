package eu.jitpay.test.task.userlocations.repository;

import eu.jitpay.test.task.userlocations.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

}
