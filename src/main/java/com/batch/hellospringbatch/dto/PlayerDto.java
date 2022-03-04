package com.batch.hellospringbatch.dto;

import lombok.Data;

// player-list.txt 매핑 클래스
@Data
public class PlayerDto {
    private String Id;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;
}
