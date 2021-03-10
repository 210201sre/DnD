package com.revature.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "dnd_characters", schema = "dnd")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "owner" })
@ToString(exclude = { "owner" })
public class DnDCharacter implements Serializable{

	private static final long serialVersionUID = -3695127612894041698L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	@JsonBackReference
	private User owner;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Race race;

	@Enumerated(EnumType.STRING)
	private MainClass mainClass;

	private byte level;
	private int exp;

	private byte hp;
	private short age;
	private short height;
	private short weight;

	@Enumerated(EnumType.STRING)
	private Alignment alignment;

	@Enumerated(EnumType.STRING)
	private Size size;

	private byte speed;
	
	@ElementCollection(targetClass = Language.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "language_known", joinColumns = @JoinColumn(name = "character_id"), schema = "dnd")
	@Column(name = "language", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Language> languages;

	@Transient
	private Set<Language> scripts = new HashSet<>();

	public void setScripts(Set<Language> language) {
		for (Language l : language) {
			switch (l) {
			case COMMON:
				this.scripts.add(Language.COMMON);
				break;
			case DWARVISH:
				this.scripts.add(Language.DWARVISH);
				break;
			case ELVISH:
				this.scripts.add(Language.ELVISH);
				break;
			case GIANT:
				this.scripts.add(Language.DWARVISH);
				break;
			case GNOMISH:
				this.scripts.add(Language.DWARVISH);
				break;
			case GOBLIN:
				this.scripts.add(Language.DWARVISH);
				break;
			case HALFING:
				this.scripts.add(Language.COMMON);
				break;
			case ORC:
				this.scripts.add(Language.DWARVISH);
				break;
			case ABYSSAL:
				this.scripts.add(Language.INFERNAL);
				break;
			case CELESTIAL:
				this.scripts.add(Language.CELESTIAL);
				break;
			case DEEPSPEECH:
				break;
			case DRACONIC:
				this.scripts.add(Language.DRACONIC);
				break;
			case INFERNAL:
				this.scripts.add(Language.INFERNAL);
				break;
			case PRIMORDIAL:
				this.scripts.add(Language.DWARVISH);
				break;
			case SYLVAN:
				this.scripts.add(Language.ELVISH);
				break;
			case UNDERCOMMON:
				this.scripts.add(Language.ELVISH);
				break;
			}
		}
	}
}
