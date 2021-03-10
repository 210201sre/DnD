package com.revature.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.revature.services.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "dnd")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements HttpSessionBindingListener, Serializable {

	private static final long serialVersionUID = 3946047219185134251L;

	public static final byte MAXCHARACTERS = 3;

	private static Map<User, HttpSession> logins = new ConcurrentHashMap<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;

	private String username;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToOne(cascade = { CascadeType.ALL}, mappedBy = "owner")
	private DungeonMaster dungeonMaster;

	@OneToMany(cascade = { CascadeType.ALL}, mappedBy = "owner")
	private List<DnDCharacter> dndCharacters;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="friends", schema="dnd", joinColumns=@JoinColumn(name="main_id"))
	@Column(name="friend_id")
	private Set<Integer> friendId;

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		HttpSession session = logins.remove(this);
		if (session != null) {
			session.invalidate();
		}
		logins.put(this, event.getSession());
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		UserService.update(this, String.format("User: %s has been logged out and updated", this.username));
		logins.remove(this);
	}

	public static Map<User, HttpSession> getAllLoggedInUsers() {
		return logins;
	}
}
