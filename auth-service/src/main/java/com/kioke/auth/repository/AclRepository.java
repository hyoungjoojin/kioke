package com.kioke.auth.repository;

import com.kioke.auth.model.AclEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AclRepository extends JpaRepository<AclEntry, String> {}
