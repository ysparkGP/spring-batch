package com.batch.hellospringbatch.dto;

// PlayerDto -> Business Logic -> PlayerSalaryDto

import lombok.Data;

@Data
public class PlayerSalaryDto {

    private String Id;
    private String lastName;
    private String firstName;
    private String position;
    private int birthYear;
    private int debutYear;

    private int salary;

    public static PlayerSalaryDto of(PlayerDto player, int salary){
        PlayerSalaryDto playerSalaryDto = new PlayerSalaryDto();
        playerSalaryDto.setId(player.getId());
        playerSalaryDto.setLastName(player.getLastName());
        playerSalaryDto.setFirstName(player.getFirstName());
        playerSalaryDto.setPosition(player.getPosition());
        playerSalaryDto.setBirthYear(player.getBirthYear());
        playerSalaryDto.setDebutYear(player.getDebutYear());
        playerSalaryDto.setSalary(salary);

        return playerSalaryDto;
    }

}
