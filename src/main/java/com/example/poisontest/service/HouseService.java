package com.example.poisontest.service;

import com.example.poisontest.dto.house.CreateOrUpdateHouseDto;
import com.example.poisontest.dto.house.HouseDto;

import java.util.List;

/**
 * @author DratkOMG
 * @created 14:08 - 04/04/2024
 */
public interface HouseService {
    HouseDto getHouseDto(Long houseId);

    HouseDto addHouse(CreateOrUpdateHouseDto createOrUpdateHouseDto);

    HouseDto updateHouse(CreateOrUpdateHouseDto houseDto, Long houseId);

    void deleteHouse(Long houseId);

    HouseDto addUserToHouse(Long houseId, Long userId);

    HouseDto addUsersToHouse(Long houseId, List<Long> userIds);

    void removeUserFromHouse(Long houseId, Long userId);
}
