package com.tripply.booking.repository;

import com.tripply.booking.constants.enums.JobStatus;
import com.tripply.booking.entity.RoomBulkJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomBulkJobRepository extends JpaRepository<RoomBulkJob, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE RoomBulkJob j SET j.status = :status, j.updatedAt = CURRENT_TIMESTAMP WHERE j.id = :jobId")
    int updateJobStatus(@Param("jobId") Long jobId, @Param("status") JobStatus status);

    Page<RoomBulkJob> findAll(Specification<RoomBulkJob> spec, Pageable pageable);

}
