package lt.tomas.test.controller.page.controllers;

import lt.tomas.test.controller.ControllerCommands;
import lt.tomas.test.model.Person;
import lt.tomas.test.model.Room;
import lt.tomas.test.model.sqlclasses.HistoryElement;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ImportAutoConfiguration
@Controller
public class IndexController {
    ControllerCommands ct= new ControllerCommands();

    @GetMapping(value = "/")
    String index(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("roomInfo", new Room());
        model.addAttribute("rooms", ct.getAllRooms());
        return "index";
    }

    @PostMapping(value = "/checkin")
    String checkin(@ModelAttribute Person person, Model model) {
        if(ct.putClient(person)){
            model.addAttribute("resultcheckin", "You checked- in");
        }else{
            model.addAttribute("resultcheckin", "All rooms are full");
        }
        model.addAttribute("rooms", ct.getAllRooms());
        return "index";
    }

    @PostMapping(value = "/checkout")
    String checkout(@ModelAttribute Person person, Model model) {
        ct.checkOut(person);
        model.addAttribute("person", new Person());
        model.addAttribute("rooms", ct.getAllRooms());
        return "index";
    }

    @GetMapping(value = "/history")
    String history(@RequestParam(value="roomNr",required=false) String nr , Model model) {
        Room room= new Room(nr);
        List<HistoryElement> hs = ct.getRoomInfo(room);

        model.addAttribute("person", new Person());
        model.addAttribute("roomInfo", new Room());
        model.addAttribute("rooms", ct.getAllRooms());
        model.addAttribute("historyinfo",hs );

        return "index";
    }
}
