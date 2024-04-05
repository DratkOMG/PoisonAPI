package com.example.poisontest.controllers;

import com.example.poisontest.dtos.house.CreateOrUpdateHouseDto;
import com.example.poisontest.dtos.house.HouseDto;
import com.example.poisontest.services.HouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author DratkOMG
 * @created 14:09 - 04/04/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/houses")
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/{house-id}")
    public ResponseEntity<HouseDto> getHouse(@PathVariable("house-id") Long houseId) {
        return ResponseEntity
                .ok(houseService.getHouseDto(houseId));
    }

    @PostMapping
    public ResponseEntity<HouseDto> addHouse(@Valid @RequestBody CreateOrUpdateHouseDto createOrUpdateHouseDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(houseService.addHouse(createOrUpdateHouseDto));
    }

    @PutMapping("/{house-id}")
    public ResponseEntity<HouseDto> updateHouse(@RequestBody CreateOrUpdateHouseDto createOrUpdateHouseDto,
                                                @PathVariable("house-id") Long houseId) {
        return ResponseEntity
                .accepted()
                .body(houseService.updateHouse(createOrUpdateHouseDto, houseId));
    }

    @DeleteMapping("/{house-id}")
    public ResponseEntity<HouseDto> deleteHouse(@PathVariable("house-id") Long houseId) {
        houseService.deleteHouse(houseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{house-id}/add-user/{user-id}")
    public ResponseEntity<HouseDto> addUserToHouse(@PathVariable("house-id") Long houseId,
                                                   @PathVariable("user-id") Long userId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(houseService.addUserToHouse(houseId, userId));
    }

    @PostMapping("/{house-id}/add-users")
    public ResponseEntity<HouseDto> addUsersToHouse(@PathVariable("house-id") Long houseId,
                                                    @RequestBody List<Long> userIds) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(houseService.addUsersToHouse(houseId, userIds));
    }

    @DeleteMapping("/{house-id}/delete-user/{user-id}")
    public ResponseEntity<HouseDto> removeUserFromHouse(@PathVariable("house-id") Long houseId,
                                                        @PathVariable("user-id") Long userId) {
        houseService.removeUserFromHouse(houseId, userId);
        return ResponseEntity.noContent().build();
    }

}
