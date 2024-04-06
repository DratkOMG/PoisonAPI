package com.example.poisontest.dtos.house;

import com.example.poisontest.models.House;
import com.example.poisontest.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DratkOMG
 * @created 14:19 - 04/04/2024
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDto {

    private String address;

    private Long ownerId;

    private List<Long> residentsId;

    public static HouseDto from(House house) {
        List<Long> residentsIdList = new ArrayList<>();
        if (house.getResidents() != null) {
            residentsIdList = house.getResidents().stream().map(User::getUserId).toList();
        }

        return HouseDto.builder()
                .address(house.getAddress())
                .ownerId(house.getOwner().getUserId())
                .residentsId(residentsIdList)
                .build();
    }

    public static List<HouseDto> from(List<House> houseList) {
        return houseList.stream()
                .map(HouseDto::from)
                .collect(Collectors.toList());
    }
}
