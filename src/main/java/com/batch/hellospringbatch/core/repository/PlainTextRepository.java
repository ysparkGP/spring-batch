package com.batch.hellospringbatch.core.repository;

import com.batch.hellospringbatch.core.domain.PlainText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlainTextRepository extends JpaRepository<PlainText, Integer> {
    // 페이징을 통해 데이터를 읽음
    Page<PlainText> findBy(Pageable pageable);
}
