package su.kotindustries.KotNotes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import su.kotindustries.KotNotes.data.UserAccount;
import su.kotindustries.KotNotes.data.UserAccountRepository;

import java.util.List;

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
}
