package io.gosaas.assessment.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Structure {

	private int sid;
	private int sitemid;
	private String description;
	private String Class;
	private String lifecyclephase;
	private String createdby;

	//ye lazmi rakhna he bhai jaan nae to response nae ana json me bethe rehna phir muh utha k 
	public Structure() {
		
	}
	
	public Structure(int sid, int sitemid, String description, String class1, String lifecyclephase, String createdby) {
		super();
		this.sid = sid;
		this.sitemid = sitemid;
		Class = class1;
		this.description = description;
		this.lifecyclephase = lifecyclephase;
		this.createdby = createdby;
	}

	public Structure(int sitemid, String description, String class1, String lifecyclephase, String createdby) {
		super();
		this.sitemid = sitemid;
		Class = class1;
		this.description = description;
		this.lifecyclephase = lifecyclephase;
		this.createdby = createdby;
	}

	public Structure(String description, String class1, String lifecyclephase, String createdby) {
		super();
		Class = class1;
		this.description = description;
		this.lifecyclephase = lifecyclephase;
		this.createdby = createdby;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSitemid() {
		return sitemid;
	}

	public void setSitemid(int sitemid) {
		this.sitemid = sitemid;
	}

	public String getclass() {
		return Class;
	}

	public void setClass(String class1) {
		Class = class1;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLifecyclephase() {
		return lifecyclephase;
	}

	public void setLifecyclephase(String lifecyclephase) {
		this.lifecyclephase = lifecyclephase;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	@Override
	public String toString() {
		return "Structure [sid=" + sid + ", sitemid=" + sitemid + ", Class=" + Class + ", description=" + description
				+ ", lifecyclephase=" + lifecyclephase + ", createdby=" + createdby
				+ "]";
	}
	
	
}
