import React, { useState } from "react";
import GoogleMap from "../components/GoogleMap"; // Assuming you have a GoogleMap component
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useKeycloak } from "@react-keycloak/web";

const AppointmentReservationPage = ({
  visitName,
  visitPrice,
  doctorName,
  doctorLocation,
  doctorEmail,
  phone,
  coordinates,
}) => {
  const [startDate, setStartDate] = useState(new Date());
  const [showPopup, setShowPopup] = useState(false);
  const { keycloak } = useKeycloak();

  const handleConfirmAppointment = () => {
    if (keycloak.authenticated) {
      // Here you can perform actions like sending email and show popup
      // For demo, we'll just toggle the popup visibility
      setShowPopup(true);
      console.log(keycloak.token)
    } else {
      // Redirect to login page
      keycloak.login();
    }
  };

  return (
    <div className="container mx-auto p-8 bg-white bg-opacity-75 rounded-lg mt-20">
      <div className="grid grid-cols-2 md:grid-cols-2 gap-6 mb-2">
        <div>
          <h1 className="text-3xl font-bold mb-4">Reserve Appointment</h1>
          {/* Visit Details */}
          <div className="mb-6">
            <h2 className="text-xl font-semibold mb-2">Visit Details</h2>
            <p>
              <span className="italic text-blue-600">Type:</span> {visitName}
            </p>
            <p>
              <span className="italic text-blue-600">Price:</span> {visitPrice}
            </p>
          </div>

          {/* Date and Time Picker */}
          <div className="mb-6">
            <h2 className="text-xl text-gray font-semibold mb-2">
              Select Date and Time
            </h2>
            <DatePicker
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              showTimeSelect
              dateFormat="Pp"
              minDate={new Date()} // Disables past dates
              maxDate={new Date(Date.now() + 30 * 24 * 60 * 60 * 1000)} // Disables dates ahead of 1 month
              preventOpenOnFocus={true}
              onKeyDown={(e) => {
                e.preventDefault();
              }}
            />
          </div>
          {/* Doctor Information */}
          <div className="mb-4">
            <h2 className="text-xl font-semibold mb-2">Doctor Information</h2>
            <p>
              <span className="italic text-blue-600">Doctor Name:</span>{" "}
              {doctorName}
            </p>
            <p>
              <span className="italic text-blue-600">Location:</span>{" "}
              {doctorLocation}
            </p>
            <p>
              <span className="italic text-blue-600">Email:</span> {doctorEmail}
            </p>
            <p>
              <span className="italic text-blue-600">Phone:</span> {phone}
            </p>
          </div>
          {/* Confirm Button */}
          <button
            className="bg-blue-500 text-white font-semibold px-6 py-3 rounded-md hover:bg-blue-600"
            onClick={handleConfirmAppointment}
          >
            Confirm Appointment
          </button>
        </div>
        {/* Map */}
        <div className="w-full h-full">
          <GoogleMap coordinates={coordinates} />
        </div>
      </div>
      {/* Popup */}
      {showPopup && (
        <div className="fixed inset-0 flex items-center justify-center">
          <div className="bg-white p-8 rounded-lg shadow-lg">
            <p>
              An email has been sent to {keycloak.tokenParsed.email} for further
              information.
            </p>
            <p>Soon a doctor will accept the reservation.</p>

            <div className="text-center">
              {" "}
              {/* Center the button */}
              <button
                className="bg-blue-500 text-white font-semibold px-4 py-2 mt-4 rounded-md hover:bg-blue-600"
                onClick={() => setShowPopup(false)}
              >
                Got it!
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default AppointmentReservationPage;
