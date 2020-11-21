package com.flight_reservation_app_5.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flight_reservation_app_5.entity.Flight;
import com.flight_reservation_app_5.entity.Passenger;
import com.flight_reservation_app_5.entity.Reservation;
import com.flight_reservation_app_5.repository.FlightRepository;
import com.flight_reservation_app_5.repository.PassengerRepository;
import com.flight_reservation_app_5.repository.ReservationRepository;
import com.flight_reservation_app_5.util.EmailUtils;
import com.flight_reservation_app_5.util.PDFGenerator;


@Controller
public class ReservationController {
	 private static String folderPath = "D:\\spring boot programs\\flight_reservation_app_5\\tickets\\";
	
	@Autowired
	FlightRepository flightRepo;
	
	@Autowired
	PassengerRepository passengerRepo;
	
	@Autowired
	ReservationRepository reservationRepo;
	
	@Autowired
	PDFGenerator pdfGen;
	
	@Autowired
	EmailUtils emailUtil ;
	
	@RequestMapping("/showCompleteReservation")
	public String showCompleteReservation(@RequestParam("flightId") Long flightId,ModelMap modelMap) {
		Optional<Flight> findById = flightRepo.findById(flightId);
		Flight flight = findById.get();
		modelMap.addAttribute("flight", flight);
		return "showCompleteReservation";
	}
	
	@RequestMapping("/confirmationReservation")
	public String confirmationReservation(@RequestParam("firstName")String firstName,@RequestParam("lastName")String lastName,@RequestParam("middleName")String middleName,@RequestParam("email")String email,@RequestParam("phone")String phone,@RequestParam("flightId")Long flightId,ModelMap modelMap) {
		Passenger passenger=new Passenger();
		passenger.setFirstName(firstName);
		passenger.setLastName(lastName);
		passenger.setMiddleName(middleName);
		passenger.setEmail(email);
		passenger.setPhone(phone);
		
		passengerRepo.save(passenger);
		
		Optional<Flight> findById = flightRepo.findById(flightId);
		Flight flight=findById.get();
		
		Reservation reservation= new Reservation();
		reservation.setCheckedIn(false);
		reservation.setNumberOfBags(0);
		reservation.setPassenger(passenger);
		reservation.setFlight(flight);
		
		reservationRepo.save(reservation);
		
		modelMap.addAttribute("firstName",firstName);
		modelMap.addAttribute("lastName",lastName);
		modelMap.addAttribute("middleName",middleName);
		modelMap.addAttribute("email",email);
		modelMap.addAttribute("phone",phone);
		modelMap.addAttribute("operatingAirlines",flight.getOperatingAirlines());
		modelMap.addAttribute("departureCity",flight.getDepartureCity());
		modelMap.addAttribute("arrivalCity",flight.getArrivalCity());
		modelMap.addAttribute("estimatedDepartureTime",flight.getEstimatedDepartureTime());
		
		String estimatedDepartureTime = flight.getEstimatedDepartureTime().toString();
		pdfGen.generatePDF(folderPath+"tickets"+passenger.getId()+".pdf",passenger.getFirstName(),passenger.getEmail(),passenger.getPhone(),flight.getOperatingAirlines(),estimatedDepartureTime,flight.getDepartureCity(),flight.getArrivalCity());
		
		emailUtil.sendItinerary(passenger.getEmail(),folderPath+"tickets"+passenger.getId()+".pdf");
		return "confirmationPage";
	}
	
}
