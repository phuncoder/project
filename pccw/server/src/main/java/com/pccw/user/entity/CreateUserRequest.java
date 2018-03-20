package com.pccw.user.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class CreateUserRequest  {

	public CreateUserRequest(Builder builder) {
		this.email = builder.email;
		this.name =  builder.name;
		this.password = builder.password;
		this.username =  builder.username;
	}
	
	public CreateUserRequest() {
		
	}

	@NotNull
	@NotBlank
	private String email;

	@NotNull
	@NotBlank
	private String name;

	@NotNull
	@NotBlank
	private String password;

	@NotNull
	@NotBlank
	private String username;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public static class Builder {
		
		private String name = "Richard West";
		private String username = "rickwest";
		private String email = "richard.west@pccw.global.hk";
		private String password = "#####DSS";
		
		public Builder() {
			
		}
		 
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public CreateUserRequest build() {
			return new CreateUserRequest(this);
		}
	}

}
