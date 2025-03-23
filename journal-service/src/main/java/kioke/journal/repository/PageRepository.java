package kioke.journal.repository;

import kioke.journal.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, String> {}
