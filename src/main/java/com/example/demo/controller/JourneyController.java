package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Journey;
import com.example.demo.repository.IJourneyRepository;

@RestController
@RequestMapping("/journeys")
public class JourneyController {
	
	@Autowired
	IJourneyRepository journeyRepository;
	
	@PostMapping()
	public ResponseEntity<Journey> save(@Valid @RequestBody Journey journey){
		
		try {
			return new ResponseEntity<>(journeyRepository.save(journey), HttpStatus.CREATED);
			
		}catch (Exception ex){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping()
	public ResponseEntity<List<Journey>> getAllJourneys(){
		
		try {
			List<Journey> list = journeyRepository.findAll();
			if(list.isEmpty() || list.size() == 0) {
				return new ResponseEntity<List<Journey>>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<List<Journey>>(list, HttpStatus.OK);
			}
		}catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Journey> getJourney(@PathVariable Long id){
		Optional<Journey> journey = journeyRepository.findById(id);
		if(journey.isPresent()) {
			return new ResponseEntity<Journey>(journey.get(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Journey> updateJourney(@Valid @RequestBody Journey journey){
		
		try {
			return new ResponseEntity<Journey>(journeyRepository.save(journey), HttpStatus.OK);			
		}catch (Exception ex) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteJourney(@PathVariable Long id){
		try {
			Optional<Journey> journey = journeyRepository.findById(id);
			if(journey.isPresent()) {
				journeyRepository.delete(journey.get());
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			}
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}catch (Exception ex) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}

}
