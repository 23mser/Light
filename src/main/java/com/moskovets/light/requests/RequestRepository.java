package com.moskovets.light.requests;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Page<Request> findAllByRequesterId(Long userId, Pageable pageable);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    @Query("select r from Request as r where lower(r.requesterName) like lower(concat('%', ?1,'%'))")
    List<Request> searchRequestsByUsername(String text, PageRequest pageRequest);
}
