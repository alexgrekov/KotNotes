package su.kotindustries.kotnotes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/")
    public String rootPage(@RequestParam(value = "param", defaultValue = "Def") String param){
        return "<http>" +
                "<head>" +
                "<tittle>" +
                "KotNotes" +
                "</tittle>" +
                "<body>" +
                "<h2>Test on</h2>" +
                "Param "+ param+ " has been sent.";
    }
}
