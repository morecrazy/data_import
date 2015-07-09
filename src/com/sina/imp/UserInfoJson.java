package com.sina.imp;

import java.util.List;

public class UserInfoJson {
	private List<Age> age_list;
	private List<Tag> it_list;
	private String gender;
	private List<Gender> gender_prob;
	private List<Crowd> crowd_list;
	private List<Attachment> attachment;
	public List<Attachment> getAttachment() {
		return attachment;
	}
	public void setAttachment(List<Attachment> attachment) {
		this.attachment = attachment;
	}
	public List<Gender> getGender_prob() {
		return gender_prob;
	}
	public void setGender_prob(List<Gender> gender_prob) {
		this.gender_prob = gender_prob;
	}
	public List<Age> getAge_list() {
		return age_list;
	}
	public void setAge_list(List<Age> age_list) {
		this.age_list = age_list;
	}
	public List<Tag> getIt_list() {
		return it_list;
	}
	public void setIt_list(List<Tag> it_list) {
		this.it_list = it_list;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<Crowd> getCrowd_list() {
		return crowd_list;
	}
	public void setCrowd_list(List<Crowd> crowd_list) {
		this.crowd_list = crowd_list;
	}
}
class Age{
	private String id;
	private String weight;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
}
class Tag
{
	private String id;
	private String weight;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
}

class Gender
{
	private String id;
	private String weight;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
}

class Crowd
{
	private String id;
	private String weight;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
}

class Attachment
{
	private String type;
	private String id;
	private String weight;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
}