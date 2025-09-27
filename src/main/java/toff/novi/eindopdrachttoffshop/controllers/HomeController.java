package toff.novi.eindopdrachttoffshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welkom bij Toffshop Webshop API!");
        response.put("version", "3.0");
        response.put("timestamp", LocalDateTime.now());
        response.put("endpoints head categories", Map.of(

                "fashion", "category/FASHION",
                "health", "category/HEALTH",
                "Kitchen", "category/KITCHEN",
                "tools", "category/TOOLS"

        ));
        return response;
    }
}
