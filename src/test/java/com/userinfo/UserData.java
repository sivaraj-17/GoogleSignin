package com.userinfo;

import java.io.Serializable;

public class UserData implements Serializable {

	private PersonalData personalData;

	public PersonalData getPersonalData() {
		return personalData;
	}

	@Override
	public String toString() {
		return "UserData{" +
				"personalData=" + personalData +
				'}';
	}
}
