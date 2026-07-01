package com.likelion.pbl.assignment.repository;

import com.likelion.pbl.assignment.domain.Assignment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByMemberId(Long memberId);

    @Query("select a from Assignment a where a.title like concat('%', :title, '%')")
    List<Assignment> findByTitleContainingWithJpql(@Param("title") String title);
}
