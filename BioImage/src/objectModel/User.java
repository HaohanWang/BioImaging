package objectModel;

public class User {
  String name;

  String address;

  String email;

  char[] password;

  int authority;

  public int getAuthority() {
    return authority;
  }

  public void setAuthority(int authority) {
    this.authority = authority;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setPassword(char[] password) {
    this.password = password;
  }

  public char[] getPassword() {
    return password;
  }

}
