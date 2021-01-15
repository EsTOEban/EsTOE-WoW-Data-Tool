package com.estoeban.blizzardwowdatatest.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface CharacterInfoRepository extends CrudRepository<CharacterInfo, String> {

    Optional<CharacterInfo> findById(Integer id);

}
