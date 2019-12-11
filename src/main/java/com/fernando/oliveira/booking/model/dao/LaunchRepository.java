/**
 * 
 */
package com.fernando.oliveira.booking.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernando.oliveira.booking.model.domain.Launch;

/**
 * @author Fernando
 *
 */
@Repository
public interface LaunchRepository extends JpaRepository<Launch, Long> {

}
