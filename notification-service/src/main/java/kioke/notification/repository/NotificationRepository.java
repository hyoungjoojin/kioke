package kioke.notification.repository;

import kioke.notification.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<User, String> {}
