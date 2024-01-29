package com.payroc.shortenurl.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 *This class represents the only table in our in memory database. 
 *The @Entity annotation tells JPA that this class is to be mapped to a database table.
 * Every instance of in this class represents a row in the table.
 * In most cases, the name of the table in the database and the name of the entity won’t be the same.
 * In this case, for example, UrlEntity class represents urls table in database
 */
@Entity
@Table(name = "urls")
@NamedQueries({
	  @NamedQuery(name = "UrlEntity.findAllUrlEntries", query = "SELECT u FROM UrlEntity u ORDER BY u.id"),
	  @NamedQuery(name = "UrlEntity.findByOriginalUrl", query = "SELECT u FROM UrlEntity u WHERE u.origUrl = :origUrl"),
	  @NamedQuery(name = "UrlEntity.findByTinyUrl", query = "SELECT u FROM UrlEntity u WHERE u.tinyUrl = :tinyUrl")
	})
public class UrlEntity {
   
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(
            name = "tinyUrlSequence",
            sequenceName = "shortUrlId_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tinyUrlSequence")
    private Long id;

    @Column(name = "tinyUrl", unique = true)
    private String tinyUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String origUrl;

    @Column(nullable = false)
    private Long clickCount = 0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTinyUrl() {
		return tinyUrl;
	}

	public void setTinyUrl(String tinyUrl) {
		this.tinyUrl = tinyUrl;
	}

	public String getOrigUrl() {
		return origUrl;
	}

	public void setOrigUrl(String fullUrl) {
		this.origUrl = fullUrl;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}
    
    
    
    
}