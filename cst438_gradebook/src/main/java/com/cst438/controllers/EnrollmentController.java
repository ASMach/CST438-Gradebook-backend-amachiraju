package com.cst438.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;

@RestController
public class EnrollmentController {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	/*
	 * endpoint used by registration service to add an enrollment to an existing
	 * course.
	 */
	@PostMapping("/enrollment")
	@Transactional
	public EnrollmentDTO addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
		
		RestTemplate httpTemplate = new RestTemplate();
		
		System.out.println("Sending http message: " + enrollmentDTO);
		ResponseEntity<EnrollmentDTO> response = httpTemplate.postForEntity("http://localhost:8081/enrollment", enrollmentDTO, EnrollmentDTO.class);
		
		HttpStatus rc = response.getStatusCode();
		
		System.out.println("HTTP status: " + rc);
		
		EnrollmentDTO returnObject = response.getBody();
		
		Course c = courseRepository.findById(enrollmentDTO.course_id).orElse(null);
        if (c != null) {
            Enrollment e = new Enrollment();
            e.setCourse(c);
            e.setStudentEmail(enrollmentDTO.studentEmail);
            e.setStudentName(enrollmentDTO.studentName);
            enrollmentRepository.save(e);
        } 
        
		System.out.println("Received Enrollment Message" + enrollmentDTO);
		
		System.out.println(returnObject);
		
		return returnObject;
	}

}
