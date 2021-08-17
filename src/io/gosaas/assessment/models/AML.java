package io.gosaas.assessment.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AML {

	private int mid;
	private int mitemid;
	private String mpart;
	private String manufacturer;
	private int registryid;
	private String amlstatus;
	private String description;
	private String mstatus;

	//ye lazmi rakhna he bhai jaan nae to response nae ana json me bethe rehna phir muh utha k 
	public AML() {
		
	}

	public AML(int mid, int mitemid, String mpart, String manufacturer, int registryid, String amlstatus,
			String description, String mstatus) {
		super();
		this.mid = mid;
		this.mitemid = mitemid;
		this.mpart = mpart;
		this.manufacturer = manufacturer;
		this.registryid = registryid;
		this.amlstatus = amlstatus;
		this.description = description;
		this.mstatus = mstatus;
	}

	public AML(String mpart, String manufacturer, int registryid, String amlstatus, String description,
			String mstatus) {
		super();
		this.mpart = mpart;
		this.manufacturer = manufacturer;
		this.registryid = registryid;
		this.amlstatus = amlstatus;
		this.description = description;
		this.mstatus = mstatus;
	}

	public AML(int mitemid, String mpart, String manufacturer, int registryid, String amlstatus, String description,
			String mstatus) {
		super();
		this.mitemid = mitemid;
		this.mpart = mpart;
		this.manufacturer = manufacturer;
		this.registryid = registryid;
		this.amlstatus = amlstatus;
		this.description = description;
		this.mstatus = mstatus;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getMitemid() {
		return mitemid;
	}

	public void setMitemid(int mitemid) {
		this.mitemid = mitemid;
	}

	public String getMpart() {
		return mpart;
	}

	public void setMpart(String mpart) {
		this.mpart = mpart;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getRegistryid() {
		return registryid;
	}

	public void setRegistryid(int registryid) {
		this.registryid = registryid;
	}

	public String getAmlstatus() {
		return amlstatus;
	}

	public void setAmlstatus(String amlstatus) {
		this.amlstatus = amlstatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMstatus() {
		return mstatus;
	}

	public void setMstatus(String mstatus) {
		this.mstatus = mstatus;
	}

	@Override
	public String toString() {
		return "AML [mid=" + mid + ", mitemid=" + mitemid + ", mpart=" + mpart + ", manufacturer=" + manufacturer
				+ ", registryid=" + registryid + ", amlstatus=" + amlstatus + ", description=" + description
				+ ", mstatus=" + mstatus + "]";
	}
	
		
}
