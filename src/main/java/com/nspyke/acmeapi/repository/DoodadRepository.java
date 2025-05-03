package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Doodad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Doodad entities.
 * Provides CRUD operations and custom query methods for Doodad entities.
 */
@Repository
public interface DoodadRepository extends JpaRepository<Doodad, Long>, JpaSpecificationExecutor<Doodad> {
}
