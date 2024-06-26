package com.example.poisontest.repository;

import com.example.poisontest.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author DratkOMG
 * @created 14:06 - 04/04/2024
 */
@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    boolean existsByHouseIdAndOwnerUserId(Long houseId, Long ownerUserId);

    boolean existsByHouseIdAndResidentsUserId(Long houseId, Long residentsUserId);
}
