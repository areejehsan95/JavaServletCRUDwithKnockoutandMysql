package io.gosaas.assessment.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Item {

	private int id;
	private String Class;
	private String description;
	
	//ye lazmi rakhna he bhai jaan nae to response nae ana json me bethe rehna phir muh utha k 
	public Item() {
		
	}
	
	public Item(int id, String class1, String description) {
		super();
		this.id = id;
		Class = class1;
		this.description = description;
	}

	public Item(String class1, String description) {
		super();
		Class = class1;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Item [id=" + id + ", Class=" + Class + ", description=" + description + "]";
	}

}
