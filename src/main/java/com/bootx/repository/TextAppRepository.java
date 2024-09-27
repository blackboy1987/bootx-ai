package com.bootx.repository;

import com.bootx.entity.TextApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public interface TextAppRepository extends JpaRepository<TextApp, Long>, JpaSpecificationExecutor<TextApp> {
}
