package org.jml.myeis.repository;

import org.jml.myeis.domain.ProcessDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BPMRepository extends JpaRepository<ProcessDetails, Long> {

}