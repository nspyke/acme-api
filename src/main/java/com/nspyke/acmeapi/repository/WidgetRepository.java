package com.nspyke.acmeapi.repository;

import com.nspyke.acmeapi.model.entity.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Widget entities.
 * Provides CRUD operations for Widget entities.
 */
@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long>, JpaSpecificationExecutor<Widget> {}
