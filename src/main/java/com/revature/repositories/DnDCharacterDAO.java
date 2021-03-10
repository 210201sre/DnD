package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.DnDCharacter;

public interface DnDCharacterDAO extends JpaRepository<DnDCharacter, Integer>{

}
