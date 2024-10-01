package guru.qa.niffler.jupiter.usersQueue;

import lombok.Data;

public class StaticUser {
    public String userName;
    public  String password;
    public  String friend;
    public  String income;
    public  String outcome;

    public StaticUser(String userName, String password, String friend, String income, String outcome) {
        this.userName = userName;
        this.password = password;
        this.friend = friend;
        this.income = income;
        this.outcome = outcome;
    }

    @Override
    public String toString() {
        return "StaticUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", friend='" + friend + '\'' +
                ", income='" + income + '\'' +
                ", outcome='" + outcome + '\'' +
                '}';
    }
}
