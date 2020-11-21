package com.flight_reservation_app_5.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flight_reservation_app_5.controller.dto.ReservationUpdateRequest;
import com.flight_reservation_app_5.entity.Reservation;
import com.flight_reservation_app_5.repository.ReservationRepository;

@RestController
public class ReservationRestfulController {
	
	@Autowired
	ReservationRepository reservationRepo;

	@GetMapping("/reservations/{id}")
	public Reservation findReservation(@PathVariable("id")Long id) {
		Optional<Reservation> findById = reservationRepo.findById(id);
		return findById.get();
	}
	@PutMapping("/reservation")
	public void UpdateReservation(@RequestBody ReservationUpdateRequest updateReq) {
		Long id = updateReq.getId();
		Optional<Reservation> findById = reservationRepo.findById(id);
		Reservation reservation = findById.get();
		reservation.setNumberOfBags(updateReq.getNumberOfBags());
		reservation.setCheckedIn(true);
		reservation.setFlight(reservation.getFlight());
		reservation.setPassenger(reservation.getPassenger());
		reservationRepo.save(reservation);
	}
}
