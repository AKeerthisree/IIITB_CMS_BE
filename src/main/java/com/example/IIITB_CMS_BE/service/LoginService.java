package com.example.IIITB_CMS_BE.service;

import com.example.IIITB_CMS_BE.beans.EmpDetails;
import com.example.IIITB_CMS_BE.beans.LoginRequest;
import com.example.IIITB_CMS_BE.beans.StudentDetails;
import com.example.IIITB_CMS_BE.beans.StudentLoginRequest;
import com.example.IIITB_CMS_BE.repository.EmpRepo;
import com.example.IIITB_CMS_BE.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmpRepo empRepo;

    @Autowired
    private StudentRepo studentRepo;

    public EmpDetails SignUp_Service(EmpDetails empDetails){
        empDetails.setStatus(true);
        empDetails.setPassword(passwordEncoder.encode((empDetails.getPassword())));
        empDetails=empRepo.save(empDetails);
        return empDetails;
    }

    public Boolean SignIn_Service(LoginRequest loginRequest){
        if(loginRequest.getRole()=="Student"){
            System.out.println(loginRequest+"HI");
            StudentDetails studentDetails=studentRepo.findByEmail(loginRequest.getEmail());
            if(passwordEncoder.matches(loginRequest.getPassword(), studentDetails.getPassword()) && studentDetails.isStatus()){
                return true;
            }
            return false;
        }
        System.out.println(loginRequest);
        EmpDetails empDetails = empRepo.findByEmail(loginRequest.getEmail());

        if(passwordEncoder.matches(loginRequest.getPassword(), empDetails.getPassword()) && empDetails.getRole().equals(loginRequest.getRole()) && empDetails.isStatus()){
            return true;
        }
        return false;
    }

    public StudentDetails StudentSignUp_service(StudentDetails studentDetails){
        studentDetails.setStatus(true);
        studentDetails.setPassword(passwordEncoder.encode(studentDetails.getPassword()));
        studentDetails=studentRepo.save(studentDetails);
        return studentDetails;
    }

    public Boolean StudentSignIn_Service(StudentLoginRequest studentLoginRequest){

            System.out.println(studentLoginRequest+"HI");
            StudentDetails studentDetails=studentRepo.findByRollNo(studentLoginRequest.getStudentID());
            if(passwordEncoder.matches(studentLoginRequest.getPassword(), studentDetails.getPassword()) && studentDetails.isStatus()){
                return true;
            }
            return false;



    }

//    public Boolean StudentSignIn_Service(LoginRequest loginRequest){
//        StudentDetails studentDetails=studentRepo.findByRollNo(loginRequest.getUsername());
//        if(passwordEncoder.matches(loginRequest.getPassword(), studentDetails.getPassword()) && studentDetails.getRole().equals(loginRequest.getRole()) && empDetails.isStatus()){
//            return true;
//        }
//        return false;
//    }
}