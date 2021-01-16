package com.estoeban.blizzardwowdatatest.repositories;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CharacterInfoRepository extends CrudRepository<CharacterInfo, Integer> {

    Optional<CharacterInfo> findById(Integer id);

}
