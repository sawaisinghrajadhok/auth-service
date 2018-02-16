/**
 * 
 */
package com.cs.je.auth.audit;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sawai
 *
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntityAuditable<U, PK extends Serializable> {
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@CreatedBy
	private U createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@LastModifiedBy
	private U lastModifiedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
	private Date lastModifiedDate;

	@JsonIgnore
	public U getCreatedBy() {
		return createdBy;
	}

	@JsonProperty
	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	@JsonIgnore
	public U getLastModifiedBy() {
		return lastModifiedBy;
	}

	@JsonProperty
	public void setLastModifiedBy(U lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "AbstractEntityAuditable [createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + ", createdOn="
				+ createdOn + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
}
