import React, { useState, useEffect } from "react";
import GoogleMap from "../components/GoogleMap";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useKeycloak } from "@react-keycloak/web";
import { useLocation } from "react-router-dom";
import axios from "axios";

const AppointmentReservationPage = () => {
  const location = useLocation();
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [showPopup, setShowPopup] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [timeslotMinutes, setTimeslotMinutes] = useState();
  const [timeSlotsTaken, setTimeSlotsTaken] = useState([]);

  const { keycloak } = useKeycloak();
  const [data, setData] = useState(
    JSON.parse(localStorage.getItem("appointmentData"))
  );

  useEffect(() => {
    if (location.state) {
      setData(location.state);
      localStorage.setItem("appointmentData", JSON.stringify(location.state));
      fetchTimeslotsTaken();
    }
  }, [location.state]);


  const fetchTimeslotsTaken = () => {
    const visitType = data===null ? location.state.visitType : data.visitType
    axios
      .get(`${import.meta.env.VITE_API_URL}/appointment/availability?visitType=${encodeURIComponent(visitType)}`, {
      })
      .then((response) => {
        setTimeslotMinutes(response.data.timeslotMinutes);
        setTimeSlotsTaken(response.data.timeSlotsTaken);
      })
      .catch((error) => {
        alert("Error in Fetching timeslots, try again later.");
      });
  };

  const handleConfirmAppointment = () => {
    if (keycloak.authenticated) {
      const headers = {
        Authorization: `Bearer ${keycloak.token}`,
      };

      const requestData = {
        startTimestamp: selectedDate.toISOString(),
        visitId: data.visitId,
        patientEmail: keycloak.tokenParsed.email,
        doctorEmail: data.doctorEmail,
        address: data.locationAddress,
      };

      setIsLoading(true);

      axios
        .post(`${process.env.REACT_APP_API_URL}/appointment`, requestData, {
          headers,
        })
        .then((response) => {
          console.log(response)
          setTimeSlotsTaken([...timeSlotsTaken, selectedDate.toISOString()]);
          setShowPopup(true);
        })
        .catch((error) => {
          alert(`${error.response.data.message}`);
        })
        .finally(() => {
          setIsLoading(false);
        });
    } else {
      keycloak.login();
    }
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
  };

  const getExcludedTimesForDate = () => {
    const excludedTimes = timeSlotsTaken
      .filter((slot) => {
        const slotDate = new Date(slot);
        return slotDate.toDateString() === selectedDate.toDateString();
      })
      .map((slot) => new Date(slot));

    return excludedTimes;
  };

  return (
    <div className="container mx-auto p-8 bg-white bg-opacity-75 rounded-lg mt-20">
      <div className="grid grid-cols-2 md:grid-cols-2 gap-6 mb-2">
        <div>
          <h1 className="text-3xl font-bold mb-4">Reserve Appointment</h1>
          <div className="mb-6">
            <h2 className="text-xl font-semibold mb-2">Visit Details</h2>
            <p>
              <span className="italic text-blue-600">Type:</span>{" "}
              {data && data.visitType}
            </p>
            <p>
              <span className="italic text-blue-600">Price:</span>{" "}
              {data && data.visitPrice} €
            </p>
          </div>
          <div className="mb-6">
            <h2 className="text-xl text-gray font-semibold mb-2">
              Select Date and Time
            </h2>
            <DatePicker
              selected={selectedDate}
              onChange={(date) => handleDateChange(date)}
              showTimeSelect
              dateFormat="Pp"
              minDate={new Date()}
              maxDate={new Date(Date.now() + 30 * 24 * 60 * 60 * 1000)}
              excludeTimes={getExcludedTimesForDate()}
              timeIntervals={timeslotMinutes}
              preventOpenOnFocus={true}
              onKeyDown={(e) => {
                e.preventDefault();
              }}
            />
          </div>
          <div className="mb-4">
            <h2 className="text-xl font-semibold mb-2">Doctor Information</h2>
            <p>
              <span className="italic text-blue-600">Name:</span>{" "}
              {data && data.doctorName}
            </p>
            <p>
              <span className="italic text-blue-600">Email:</span>{" "}
              {data && data.doctorEmail}
            </p>
            <p>
              <span className="italic text-blue-600">Phone:</span>{" "}
              {data && data.doctorPhoneNumber}
            </p>
          </div>
          <div className="mb-4">
            <h2 className="text-xl font-semibold mb-2">Location Information</h2>
            <p>
              <span className="italic text-blue-600">Address:</span>{" "}
              {data && data.locationAddress}
            </p>
            <p>
              <span className="italic text-blue-600">Location:</span>{" "}
              {data && data.locationName}
            </p>
            <p>
              <span className="italic text-blue-600">Email:</span>{" "}
              {data && data.contactInfo.email}
            </p>
            <p>
              <span className="italic text-blue-600">Phone:</span>{" "}
              {data && data.contactInfo.phoneNumber}
            </p>
          </div>
          <button
            className="bg-blue-500 text-white font-semibold px-4 py-2 mt-4 rounded-md hover:bg-blue-600"
            onClick={handleConfirmAppointment}
            disabled={isLoading}
          >
            {isLoading ? (
              <div className="flex items-center">
                <span className="mr-2">Confirming...</span>
                <svg
                  className="animate-spin h-5 w-5 text-white"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                >
                  <circle
                    className="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    strokeWidth="4"
                  ></circle>
                  <path
                    className="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                  ></path>
                </svg>
              </div>
            ) : (
              "Confirm Appointment"
            )}
          </button>
        </div>
        <div className="w-full h-full">
        {data && data.locationAddress && (
  <div style={{ height: "100%", width: "100%" }}>
    <GoogleMap center={data.locationAddress} locations={[data.locationAddress]} />
  </div>
)}
        </div>
      </div>
      {showPopup && (
        <div className="fixed inset-0 flex items-center justify-center">
          <div className="bg-white p-8 rounded-lg shadow-lg">
            <p>
              Thank you! A doctor will let you know if the appointment is confirmed very soon!
            </p>
            <p>Be sure to regurarly check your email.</p>
            <div className="text-center">
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
