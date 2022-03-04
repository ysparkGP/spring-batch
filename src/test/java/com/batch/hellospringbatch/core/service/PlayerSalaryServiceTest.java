package com.batch.hellospringbatch.core.service;

import com.batch.hellospringbatch.dto.PlayerDto;
import com.batch.hellospringbatch.dto.PlayerSalaryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Year;

import static org.mockito.Mockito.*;

public class PlayerSalaryServiceTest {

    private PlayerSalaryService playerSalaryService;

    @BeforeEach
    public void setuo(){
        playerSalaryService = new PlayerSalaryService();
    }

    @Test
    public void calcSalary(){
        // 테스트는 언제 돌려도 일관되게 결과를 도출해야한다.
        // 그럼으로 Year 클래스의 now 메서드를 컨트롤해야하는데, now 메서드는 static 임으로
        // 그냥 mocking 해서는 에러가 발생

        // Given
        Year mockYear = mock(Year.class);
        when(mockYear.getValue()).thenReturn(2022);
        // static 메서드 모킹
        Mockito.mockStatic(Year.class).when(Year::now).thenReturn(mockYear);

        PlayerDto mockPlayer = mock(PlayerDto.class);
        when(mockPlayer.getBirthYear()).thenReturn(1980);

        // When
        PlayerSalaryDto result = playerSalaryService.calcSalary(mockPlayer);

        // Then
        Assertions.assertEquals(result.getSalary(), 42000000);
    }
}
