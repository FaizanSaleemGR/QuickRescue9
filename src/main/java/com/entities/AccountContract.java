package com.entities;

import java.io.Serializable;
import java.util.Date;

public class AccountContract implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Integer contractId; // PK of this class
	private Date startDate;
	private Date endDate;
	private Integer contactsLimit;
	private Integer loginsLimit;

	private boolean editable;


	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getContactsLimit() {
		return contactsLimit;
	}
	public void setContactsLimit(Integer contactsLimit) {
		this.contactsLimit = contactsLimit;
	}
	public Integer getLoginsLimit() {
		return loginsLimit;
	}
	public void setLoginsLimit(Integer loginsLimit) {
		this.loginsLimit = loginsLimit;
	}

	public boolean getEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}





}
