package com.batch.hellospringbatch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
// 엔티티의 일부 값만 변경되었을때, 그 값들에 대해서만 변경되는 쿼리가 실행되도록 도와주는 어노테이션
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "result_text")
public class ResultText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String text;
}
