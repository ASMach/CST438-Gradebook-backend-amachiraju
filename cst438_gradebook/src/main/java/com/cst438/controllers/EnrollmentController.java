package com.cst438.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.rabbitmq.client.AMQP.Queue;

@RestController
public class EnrollmentController {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	Queue queue;

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
		
		System.out.println(returnObject);
		
		return returnObject;
	}

}
