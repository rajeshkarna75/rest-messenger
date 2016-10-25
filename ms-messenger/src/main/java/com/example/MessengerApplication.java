package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories
public class MessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
	}
}

@RestController
@RequestMapping("messages")
class MessageRestController {

	@Autowired
	ShareRepository shareRepository;

	@RequestMapping("{messageId}/shares")
	public List<Share> getSharesByMessageId(@PathVariable Long messageId) {

		return shareRepository.findByMessageId(messageId);
	}
	
	@RequestMapping("{messageId}/shares/{id}")
	public Share getSharesByMessageIdAndShareId(@PathVariable Long messageId, @PathVariable Long id) {

		return shareRepository.findByIdAndMessageId(id, messageId);
	}

}

@RepositoryRestResource
interface MessageRepository extends PagingAndSortingRepository<Message, Long> {

}

@RepositoryRestResource
interface ShareRepository extends PagingAndSortingRepository<Share, Long> {

	List<Share> findByMessageId(Long messageId);
	Share findByIdAndMessageId(Long id, Long messageId);
}

@Entity
class Message {

	@Id
	@GeneratedValue
	private Long id;
	private String message;
	private String senderName;
	private Date created;

	@JoinColumn(name = "messageId", referencedColumnName = "id")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Share> shares = new ArrayList<Share>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

}

@Entity
class Share {

	@Id
	@GeneratedValue
	private Long id;
	private Long messageId;
	private String personName;
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
