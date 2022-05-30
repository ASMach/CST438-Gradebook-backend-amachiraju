package com.cst438.services;


import java.util.Optional;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cst438.domain.Course;
import com.cst438.domain.CourseDTOG;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;


public class RegistrationServiceMQ extends RegistrationService {

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	Queue queue;

	public RegistrationServiceMQ() {
		System.out.println("MQ registration service ");
	}

	// ----- configuration of message queues

	@Autowired
	Queue registrationQueue;


	// ----- end of configuration of message queue

	// receiver of messages from Registration service
	
	@RabbitListener(queues = "gradebook-queue")
	@Transactional
	public void receive(EnrollmentDTO enrollmentDTO) {
		Course c = courseRepository.findById(enrollmentDTO.course_id).orElse(null);
        if (c != null) {
            Enrollment e = new Enrollment();
            e.setCourse(c);
            e.setStudentEmail(enrollmentDTO.studentEmail);
            e.setStudentName(enrollmentDTO.studentName);
            enrollmentRepository.save(e);
        } 
        
		System.out.println("Received Enrollment Message" + enrollmentDTO);
		
		
	}

	// sender of messages to Registration Service
	@Override
	public void sendFinalGrades(int course_id, CourseDTOG courseDTO) {
		 
		System.out.println("Sending Final Grade message" + courseDTO + "...");
		rabbitTemplate.convertAndSend(queue.getName(), courseDTO);
		System.out.println("Final Grade message sent");
		
	}

}
