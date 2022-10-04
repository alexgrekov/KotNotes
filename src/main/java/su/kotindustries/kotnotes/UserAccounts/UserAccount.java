package su.kotindustries.kotnotes.UserAccounts;
import org.springframework.data.annotation.Id;

public class UserAccount {

    @Id
    public String id;

    public String login;
    public String passwordHash;
    public int accessLevel;

    public UserAccount() {}

    public UserAccount(String login, String passwordHash, int accessLevel) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, login='%s', accessLevel='%s']",
                id, login, accessLevel);
    }

}