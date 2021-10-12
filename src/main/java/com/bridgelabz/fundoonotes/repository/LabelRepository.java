package com.bridgelabz.fundoonotes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.Label;

@Repository
public interface LabelRepository  extends JpaRepository<Label, Long>{
	
	public Optional<Label> findByLabelName(String labelName);

}
