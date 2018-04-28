package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


//    @RequestMapping("/")
//    public String index (Model model)
//    {
//    	model.addAttribute("title", "Home Page");
//        return "index";
//    }


    @RequestMapping("/student/add")
    public String add (Model model)
    {
    	model.addAttribute("title", "Add Student");
        return "form-add";
    }

    @RequestMapping("/student/search")
    public String search (Model model)
    {
    	model.addAttribute("title", "Search Student");
        return "search";
    }
    
    @RequestMapping("/student/add/submit")
    public String addSubmit (Model model,
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa);
        model.addAttribute("title", "Submit Student");
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);
        model.addAttribute("title", "View Student");
        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);
        model.addAttribute("title", "View Student");
        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);
        model.addAttribute("title", "View All Student");
        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {   
        StudentModel student = studentDAO.selectStudent (npm);
        model.addAttribute("title", "Delete Student");
        if (student != null) {
        	studentDAO.deleteStudent (npm);
            model.addAttribute ("student", student);
            return "delete";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping("/student/update/{npm}")
    public String update(Model model, @PathVariable(value = "npm") String npm)
    {   
        StudentModel student = studentDAO.selectStudent(npm);
        model.addAttribute("title", "Update Student");
        if (student != null) {
        	studentDAO.updateStudent(student);
            model.addAttribute ("student", student);
            return "form-update";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping(value="/student/update/submit", method = RequestMethod.POST)
    public String updateSubmit(Model model, @ModelAttribute StudentModel student)
//    		@RequestParam (value="npm", required=false) String npm,
//    		@RequestParam (value="name", required=false) String name,
//    		@RequestParam (value="gpa", required=false) double gpa)
    {   
//    	StudentModel student = studentDAO.selectStudent(npm);
//    	student.setNpm(npm);
//    	student.setName(name);
//    	student.setGpa(gpa);
    	model.addAttribute("title", "Submit Update Student");
    	studentDAO.updateStudent(student);
        return "success-update";
    }
    
    

}
