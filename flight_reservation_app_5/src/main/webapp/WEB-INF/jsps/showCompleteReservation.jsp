<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Show Complete Reservation</title>
</head>
<body>
	<h2>Flight Details</h2>

	Operating Airlines: ${flight.operatingAirlines}
	<br /> Departure City: ${flight.departureCity}
	<br /> Arrival City: ${flight.arrivalCity}
	<br /> Departure Date && Time: ${flight.estimatedDepartureTime}
	<br />

<h2>Enter Passenger Details</h2>
	<form action="confirmationReservation" method="post">
		<pre>
first name: <input type="text" name="firstName" />
last name: <input type="text" name="lastName" />
middle name: <input type="text" name="middleName" />
email : <input type="text" name="email" />
phone: <input type="text" name="phone" />
<input type="hidden" name="flightId" value="${flight.id}" />

<h2>Enter the payment details</h2>

Name:<input type="text"name="name"/>
Card Number:<input type="text"name="cardNumber"/>
CVV Code:<input type="text"name="cvvcode"/>
Expiry Date:<input type="text"name="expiryDate"/>
Amount:<input type="text"name="Amount"/>
<input type="submit" value="save" />
	</pre>
	</form>
</body>
</html>