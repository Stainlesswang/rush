package allen.api.rush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author AllenWong
 * @date 2020/2/3 3:18 PM
 */
@Controller
public class HtmlController {
    @RequestMapping("/index")
    public String testHtml(){
        return "fenye";
    }
}
