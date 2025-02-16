package com.kioke.journal.repository;

import com.kioke.journal.model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfRepository extends JpaRepository<Shelf, String> {}
