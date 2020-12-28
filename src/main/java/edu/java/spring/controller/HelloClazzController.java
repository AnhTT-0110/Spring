package edu.java.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
public class HelloClazzController {
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView printMessage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("message", "Hello Java Clazz!");
        return mv;
    }
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView printMessage(@RequestParam(value = "data", required = false) String message) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", message);
        return mv;
    }
}
