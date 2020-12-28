package edu.java.spring.controller;

import edu.java.spring.dao.StudentDAO;
import edu.java.spring.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Path;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;


    @RequestMapping(value ="student/add", method= RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("student.form", "command", new Student());
    }

    @RequestMapping(value = "student/save", method = RequestMethod.POST)
    public ModelAndView save(@Valid @ModelAttribute("command") Student student, BindingResult result){
        ModelAndView mv = new ModelAndView();
        if(result.hasErrors()) {
            mv = new ModelAndView("student.form", "command", student);
            mv.addObject("errors", result);
        }
        else {
           // studentDAO.insert(student);
            mv = new ModelAndView("redirect:/student/list");
        }

        if(student.getId() > 0) {
            studentDAO.update(student);
        } else {
            studentDAO.insert(student);
        }

        return mv;
    }

    @RequestMapping(value = "/student/list", method = RequestMethod.GET)
    public ModelAndView listStudent() {
        ModelAndView model = new ModelAndView();
        model.setViewName("student.list");
        model.addObject("students", studentDAO.list());
        return model;
    }

    @RequestMapping (value= "/student/delete/{id}")
    public String delete(@PathVariable String id) {
        studentDAO.delete(id);
        return "redirect:/student/list";
    }

    @RequestMapping (value= "/student/edit/{id}")
    public ModelAndView edit(@PathVariable String id) {
        Student student = studentDAO.get(id);
        return new ModelAndView("../student.form", "command", student);
    }

    @RequestMapping (value= "student/edit/save", method = RequestMethod.POST)
    public String saveEdit() {
        return "forward:/student/save";
    }


    @RequestMapping(value= "/student/json/{id}", method = RequestMethod.GET)
    public @ResponseBody Student viewJson(@PathVariable String id) {
        return studentDAO.get(id);
    }

    @RequestMapping(value = "/student/list", method = RequestMethod.POST)
    public ModelAndView listStudents(
            @RequestParam(value = "q", required=false) String query) {
        return null;
    }

    @RequestMapping(value = "/student/avatar/save", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) return "student.error";
        byte[] bytes = file.getBytes();
        System.out.println("found --- > " + bytes.length);
        return "redirect:/student/list";
    }

    private Path getImageFile(HttpServletRequest request,String id) {
        ServletContext servletContext = request.getSession().getServletContext();
        String diskPath = servletContext.getRealPath("/");
        File folder = new File(diskPath + File.separator + "avatar" );
        folder.mkdirs();
        return (Path) new File(folder, String.valueOf(id)+ ".jpg");
    }





}
