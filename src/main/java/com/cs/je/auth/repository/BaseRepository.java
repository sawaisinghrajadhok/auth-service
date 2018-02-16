/**
 * 
 */
package com.cs.je.auth.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * @author sawai
 *
 */

@Primary
@NoRepositoryBean
public interface BaseRepository<T, PK extends Serializable> extends JpaRepository<T, PK> {
	
	@Query("select e from #{#entityName} e WHERE e.tenantId = ?#{principal.tenantId}	")
	public List<T> findAll();
	
	@Query("select e from #{#entityName} e where e.tenantId = ?#{principal.tenantId} and e.id =:id")
	public T findByOne(@Param("id") Long id);
	
	@Transactional
    @Modifying
	@Query("delete from #{#entityName} e where e.tenantId = ?#{principal.tenantId} and e.id =:id")
	public void deleteTenantSensitive(@Param("id") Long id);
	
}
