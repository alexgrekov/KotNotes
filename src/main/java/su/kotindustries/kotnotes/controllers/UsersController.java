package su.kotindustries.kotnotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import su.kotindustries.kotnotes.UserAccounts.UserAccount;
import su.kotindustries.kotnotes.UserAccounts.UserAccountRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

@Controller
public class UsersController {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/users")
    public String usersPage(Model model){
        Iterable<UserAccount> userAccounts = userAccountRepository.findAll();
        model.addAttribute("userAccounts", userAccounts);
        return "UsersView";
    }
    @PostMapping("/api/user/add")
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
        return "redirect:/users";
    }
    @PostMapping("/api/user/delete")
    public String userDel(@RequestParam(value = "userId") String userId){
        if (userAccountRepository.existsById(userId)){
            userAccountRepository.deleteById(userId);
        }
        return "redirect:/users";
    }
}
