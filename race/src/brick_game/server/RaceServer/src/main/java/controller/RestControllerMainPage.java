package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RestControllerMainPage {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView redirect() {
    return new ModelAndView("redirect:" + "http://localhost:8080/play/main.html");
  }
}