package com.batch.hellospringbatch.job.player;

import com.batch.hellospringbatch.dto.PlayerDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

// 라인을 어떻게 객체로 매핑할지 구분
public class PlayerFieldSetMapper implements FieldSetMapper<PlayerDto> {
    @Override
    public PlayerDto mapFieldSet(FieldSet fieldSet) throws BindException {
        PlayerDto dto = new PlayerDto();
        dto.setId(fieldSet.readString(0));
        dto.setLastName(fieldSet.readString(1));
        dto.setFirstName(fieldSet.readString(2));
        dto.setPosition(fieldSet.readString(3));
        dto.setBirthYear(fieldSet.readInt(4));
        dto.setDebutYear(fieldSet.readInt(5));
        return dto;
    }
}
