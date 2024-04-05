package com.example.poisontest.services.impl;

import com.example.poisontest.dtos.house.CreateOrUpdateHouseDto;
import com.example.poisontest.dtos.house.HouseDto;
import com.example.poisontest.exceptions.NotFoundException;
import com.example.poisontest.exceptions.UserAlreadyExistsException;
import com.example.poisontest.exceptions.UserNotOwnerException;
import com.example.poisontest.models.House;
import com.example.poisontest.models.Users;
import com.example.poisontest.repositories.HouseRepository;
import com.example.poisontest.services.HouseService;
import com.example.poisontest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.poisontest.dtos.house.HouseDto.from;

/**
 * @author DratkOMG
 * @created 14:08 - 04/04/2024
 */
@RequiredArgsConstructor
@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    private final UserService userService;

    @Override
    public HouseDto getHouseDto(Long houseId) {
        House house = getHouseOrThrow(houseId);

        return from(house);
    }


    @Override
    public HouseDto addHouse(CreateOrUpdateHouseDto createOrUpdateHouseDto) {
        Users users;
        if (createOrUpdateHouseDto.getOwnerId() == null) {
            users = userService.getAuthenticationUser();
        } else {
            users = userService.getUserById(createOrUpdateHouseDto.getOwnerId());
        }

        House house = House.builder()
                .address(createOrUpdateHouseDto.getAddress())
                .owner(users)
                .build();

        houseRepository.save(house);

        return from(house);
    }


    @Override
    public HouseDto updateHouse(CreateOrUpdateHouseDto createOrUpdateHouseDto,
                                Long houseId) {
        Users authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can update the house!");
        }

        House houseToUpdate = getHouseOrThrow(houseId);
        if (createOrUpdateHouseDto.getOwnerId() != null) {
            Users usersToBeOwner = userService.getUserById(createOrUpdateHouseDto.getOwnerId());
            houseToUpdate.setOwner(usersToBeOwner);
        }

        houseToUpdate.setAddress(createOrUpdateHouseDto.getAddress());

        houseRepository.save(houseToUpdate);

        return from(houseToUpdate);
    }

    @Override
    public void deleteHouse(Long houseId) {
        Users authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can delete the house!");
        }

        houseRepository.deleteById(houseId);
    }

    @Override
    public HouseDto addUserToHouse(Long houseId, Long userId) {
        Users authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can add user to the house!");
        }

        if (isResident(houseId, userId)) {
            throw new UserAlreadyExistsException("User " + userId + " is here");
        }

        House houseToAddUser = getHouseOrThrow(houseId);
        Users userToAddToHouse = userService.getUserById(userId);

        houseToAddUser.getResidents().add(userToAddToHouse);
        houseRepository.save(houseToAddUser);

        return from(houseToAddUser);
    }

    @Override
    public HouseDto addUsersToHouse(Long houseId, List<Long> userIds) {
        Users authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can add users to the house!");
        }

        List<Users> usersToAddToHouse = userService.getAllUserByIds(userIds);
        House houseToAddUser = getHouseOrThrow(houseId);

        for (Users users : usersToAddToHouse) {
            if (isResident(houseId, users.getUserId())) {
                throw new UserAlreadyExistsException("User " + users.getUserId() + " is here");
            }
        }

        houseToAddUser.getResidents().addAll(usersToAddToHouse);
        houseRepository.save(houseToAddUser);

        return from(houseToAddUser);
    }

    @Override
    public void removeUserFromHouse(Long houseId, Long userId) {
        Users authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can remove user from the house!");
        }

        House houseToRemoveUser = getHouseOrThrow(houseId);

        if (isResident(houseId, userId)) {
            Users userToRemoveFromHouse = userService.getUserById(userId);
            houseToRemoveUser.getResidents().remove(userToRemoveFromHouse);

            houseRepository.save(houseToRemoveUser);
        }


    }

    private House getHouseOrThrow(Long houseId) {
        return houseRepository.findById(houseId).orElseThrow(() ->
                new NotFoundException("House " + houseId + " not found")
        );
    }

    private boolean isNotTheOwner(Long houseId, Long userId) {
        return !houseRepository.existsByHouseIdAndOwnerUserId(houseId, userId);
    }

    private boolean isResident(Long houseId, Long userId) {
        return houseRepository.existsByHouseIdAndResidentsUserId(houseId, userId);
    }



}
