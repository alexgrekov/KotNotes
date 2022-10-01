package su.kotindustries.KotNotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/")
    public String rootPage(@RequestParam(value = "param", defaultValue = "Def") String param){

        return "<http>" +
                "<head>" +
                "<tittle>" +
                "KotNotes" +
                "</tittle>" +
                "<body>" +
                "<h2>Test on</h2>" +
                "Param "+ param+ " has been sent." +
                "<a href='/users'>User Management</a>";

    }
    @GetMapping("/users")
    public String usersPage(){
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        StringBuilder rows = new StringBuilder();
        for (UserAccount user: userAccounts
             ) {
            rows.append("id: ").append(user.id).append(" login").append(user.login);
        }
        return "<http>" +
                "<head>" +
                "<tittle>" +
                "KotNotes" +
                "</tittle>" +
                "<body>" +
                "<form action='/users/add' method='post'>" +
                "<input type='text' name='userLogin' placeholder='login'/>" +
                "<input type='text' name='userPassword' placeholder='password'/>" +
                "<input type='text' name='userAccessLevel' placeholder='access'/>" +
                "<input type='submit' name='Add'/>" +
                "</form>"+
                "<h2>users</h2>" +
                rows;


    }
    @PostMapping("/users/add")
    public String userAdd(@RequestParam(value = "userLogin", defaultValue = "user") String userLogin,
                          @RequestParam(value = "userPassword", defaultValue = "password") String userPassword,
                          @RequestParam(value = "userAccessLevel", defaultValue = "access") String userAccessLevel){
        int accessLevel = Integer.parseInt(userAccessLevel);
        String stringHash;
        try {
            byte[] passwordBytes = userPassword.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(passwordBytes);
            stringHash = Arrays.toString(hash);
        } catch (Exception ignored){
            stringHash = "UNKNOWN_PASSWORD";
        }

        userAccountRepository.save(new UserAccount(userLogin, stringHash, accessLevel));
        return "User "+userLogin+" with "+stringHash+" hash, AL = "+accessLevel+" has been added.";
    }

}
