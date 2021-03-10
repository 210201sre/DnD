package com.revature.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="dungeon_masters", schema="dnd")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude={"owner"}) @ToString(exclude={"owner"})
public class DungeonMaster implements Serializable{

	private static final long serialVersionUID = -3863153870617828745L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private float ranking;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	@JsonBackReference
	private User owner;
}
