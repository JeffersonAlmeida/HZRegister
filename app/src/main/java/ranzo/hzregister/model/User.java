package ranzo.hzregister.model;

public final class User {
	
	private Long id;
	private String fullName;
	private String email;
	private String cpf;
	private String phone;
	private String password;
	private String gender;
	private String birthday;
	private String state;
	
	private User ( Builder builder ) {
		this.id = builder.id;
		this.fullName = builder.fullName;
		this.email = builder.email;
		this.cpf = builder.cpf;
		this.phone = builder.phone;
		this.password = builder.password;
		this.gender = builder.gender;
		this.birthday = builder.birthday;
		this.state = builder.state;
		
	}
	
	public Long getId() {
		return id;
	}
	public String getFullName() {
		return fullName;
	}
	public String getEmail() {
		return email;
	}
	public String getCpf() {
		return cpf;
	}
	public String getPhone() {
		return phone;
	}
	public String getPassword() {
		return password;
	}
	public String getGender() {
		return gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return getFullName();
	}
	
	public String toStr() {
		return "User [id=" + id + ", fullName=" + fullName + ", email=" + email
				+ ", cpf=" + cpf + ", phone=" + phone + ", password="
				+ password + ", gender=" + gender + ", birthday=" + birthday
				+ ", State=" + state + "]";
	}
	public static class Builder {
		
		private Long id;
		private String fullName;
		private String email;
		private String cpf;
		private String phone;
		private String password;
		private String gender;
		private String birthday;
		private String state;
		
		public Builder id(int id){	this.id = (long) id; return this; }
		public Builder id(Long id){	this.id = id; return this; }
		public Builder fullName(String fullName){	this.fullName = fullName; return this; }
		public Builder email(String email){ this.email = email; return this; }
		public Builder cpf(String cpf){ this.cpf = cpf; return this; }
		public Builder phone(String phone){ this.phone = phone; return this; }
		public Builder password(String password){this.password = password; return this; }
		public Builder gender(String gender){ this.gender = gender; return this; }
		public Builder birthday(String birthday){ this.birthday = birthday; return this;  }
		public Builder state(String state){ this.state = state; return this;  }
		
		public User build(){
			return new User(this);
		}
		
	}
	
}
