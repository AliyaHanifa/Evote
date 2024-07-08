package com.example;

public class Login {
    private String username;
    private String password;
    private String pilihanRole;

    public Login (String username, String password, String pilihanRole){
        this.username = username;
        this.password = password;
        this.pilihanRole = pilihanRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPilihanRole() {
        return pilihanRole;
    }

    public void setPilihanRole(String pilihanRole) {
        this.pilihanRole = pilihanRole;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + pilihanRole + '\'' +
                '}';
    }
}
