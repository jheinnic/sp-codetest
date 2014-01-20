package hello;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }}
