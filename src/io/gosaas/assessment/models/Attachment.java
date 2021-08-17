package io.gosaas.assessment.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Attachment {

	private int aid;
	private int itemid;
	private String filename;
	private String description;
	private String category;
	private String shared;
	private String checkedoutby;
	private int revision;

	//ye lazmi rakhna he bhai jaan nae to response nae ana json me bethe rehna phir muh utha k 
	public Attachment() {
		
	}

	public Attachment(int aid, int itemid, String filename, String description, String category, String shared,
			String checkedoutby, int revision) {
		super();
		this.aid = aid;
		this.itemid = itemid;
		this.filename = filename;
		this.description = description;
		this.category = category;
		this.shared = shared;
		this.checkedoutby = checkedoutby;
		this.revision = revision;
	}

	public Attachment(int itemid, String filename, String description, String category, String shared,
			String checkedoutby, int revision) {
		super();
		this.itemid = itemid;
		this.filename = filename;
		this.description = description;
		this.category = category;
		this.shared = shared;
		this.checkedoutby = checkedoutby;
		this.revision = revision;
	}

	public Attachment(String filename, String description, String category, String shared,
			String checkedoutby, int revision) {
		super();
		this.filename = filename;
		this.description = description;
		this.category = category;
		this.shared = shared;
		this.checkedoutby = checkedoutby;
		this.revision = revision;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getShared() {
		return shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}

	public String getCheckedoutby() {
		return checkedoutby;
	}

	public void setCheckedoutby(String checkedoutby) {
		this.checkedoutby = checkedoutby;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}


	@Override
	public String toString() {
		return "Attachment [aid=" + aid + ", itemid=" + itemid + ", filename=" + filename + ", description="
				+ description + ", category=" + category + ", shared=" + shared + ", checkedoutby=" + checkedoutby
				+ ", revision=" + revision + "]";
	}
	
	
	
}
