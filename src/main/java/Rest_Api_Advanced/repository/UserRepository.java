package Rest_Api_Advanced.repository;

import Rest_Api_Advanced.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT user_id FROM orders GROUP BY user_id ORDER BY SUM(cost) DESC LIMIT 1",
            nativeQuery = true)
    Long getTheWealthiestUsersId();
}
