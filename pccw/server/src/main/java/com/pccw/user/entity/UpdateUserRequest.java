package com.pccw.user.entity;

public class UpdateUserRequest {

	private String email;
	
	private String name;
	
	private String password;
	
	private String username;

	public UpdateUserRequest(Builder builder) {

		this.email = builder.email;
		this.name =  builder.name;
		this.password = builder.password;
		this.username =  builder.username;
	}

	
	public UpdateUserRequest() {
		
	}
	
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

		public UpdateUserRequest build() {
			return new UpdateUserRequest(this);
		}

	}
	
	
}
