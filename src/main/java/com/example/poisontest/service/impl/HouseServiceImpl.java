package com.example.poisontest.service.impl;

import com.example.poisontest.dto.house.CreateOrUpdateHouseDto;
import com.example.poisontest.dto.house.HouseDto;
import com.example.poisontest.exception.NotFoundException;
import com.example.poisontest.exception.UserAlreadyExistsException;
import com.example.poisontest.exception.UserNotOwnerException;
import com.example.poisontest.model.House;
import com.example.poisontest.model.User;
import com.example.poisontest.repository.HouseRepository;
import com.example.poisontest.service.HouseService;
import com.example.poisontest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.poisontest.dto.house.HouseDto.from;

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
        User user;
        if (createOrUpdateHouseDto.getOwnerId() == null) {
            user = userService.getAuthenticationUser();
        } else {
            user = userService.getUserById(createOrUpdateHouseDto.getOwnerId());
        }

        House house = House.builder()
                .address(createOrUpdateHouseDto.getAddress())
                .owner(user)
                .build();

        houseRepository.save(house);

        return from(house);
    }


    @Override
    public HouseDto updateHouse(CreateOrUpdateHouseDto createOrUpdateHouseDto,
                                Long houseId) {
        User authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can update the house!");
        }

        House houseToUpdate = getHouseOrThrow(houseId);
        if (createOrUpdateHouseDto.getOwnerId() != null) {
            User userToBeOwner = userService.getUserById(createOrUpdateHouseDto.getOwnerId());
            houseToUpdate.setOwner(userToBeOwner);
        }

        houseToUpdate.setAddress(createOrUpdateHouseDto.getAddress());

        houseRepository.save(houseToUpdate);

        return from(houseToUpdate);
    }

    @Override
    public void deleteHouse(Long houseId) {
        User authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can delete the house!");
        }

        houseRepository.deleteById(houseId);
    }

    @Override
    public HouseDto addUserToHouse(Long houseId, Long userId) {
        User authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can add user to the house!");
        }

        if (isResident(houseId, userId)) {
            throw new UserAlreadyExistsException("User " + userId + " is here");
        }

        House houseToAddUser = getHouseOrThrow(houseId);
        User userToAddToHouse = userService.getUserById(userId);

        houseToAddUser.getResidents().add(userToAddToHouse);
        houseRepository.save(houseToAddUser);

        return from(houseToAddUser);
    }

    @Override
    public HouseDto addUsersToHouse(Long houseId, List<Long> userIds) {
        User authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can add users to the house!");
        }

        List<User> userToAddToHouse = userService.getAllUserByIds(userIds);
        House houseToAddUser = getHouseOrThrow(houseId);

        for (User user : userToAddToHouse) {
            if (isResident(houseId, user.getUserId())) {
                throw new UserAlreadyExistsException("User " + user.getUserId() + " is here");
            }
        }

        houseToAddUser.getResidents().addAll(userToAddToHouse);
        houseRepository.save(houseToAddUser);

        return from(houseToAddUser);
    }

    @Override
    public void removeUserFromHouse(Long houseId, Long userId) {
        User authenticationUser = userService.getAuthenticationUser();

        if (isNotTheOwner(houseId, authenticationUser.getUserId())) {
            throw new UserNotOwnerException("Only owner can remove user from the house!");
        }

        House houseToRemoveUser = getHouseOrThrow(houseId);

        if (isResident(houseId, userId)) {
            User userToRemoveFromHouse = userService.getUserById(userId);
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
