package su.kotindustries.KotNotes.UserAccounts;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccountRepository extends MongoRepository<UserAccount, String>{

    public UserAccount findByLogin(String login);
    public List<UserAccount> findByAccessLevel(String accessLevel);

}