package guru.qa.niffler.jupiter.usersQueue;

public class UserQueueRecord {
    public UserType userType;
    public StaticUser staticUser;

    public UserQueueRecord(StaticUser staticUser, UserType ut) {
        this.staticUser = staticUser;
        this.userType = ut;
    }
}
