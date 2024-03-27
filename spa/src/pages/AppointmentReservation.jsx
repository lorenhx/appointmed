import React, { useState, useEffect } from "react";
import GoogleMap from "../components/GoogleMap"; // Assuming you have a GoogleMap component
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
  const { keycloak } = useKeycloak();
  const [data, setData] = useState(
    JSON.parse(localStorage.getItem("appointmentData"))
  );

  useEffect(() => {
    if (location.state) {
      setData(location.state);
      localStorage.setItem("appointmentData", JSON.stringify(location.state));
    }
  }, []);

  const handleConfirmAppointment = () => {
    if (keycloak.authenticated) {
      const headers = {
        Authorization: `Bearer ${keycloak.token}`
      };

      const requestData = {
        startTimestamp: selectedDate.toISOString(),
        visitId: data.visitId,
        patientEmail: keycloak.tokenParsed.email,
        doctorEmail: data.doctorEmail,
        address: data.locationAddress
      };

      setIsLoading(true);

      axios
        .post("http://localhost:9080/api/appointment", requestData, {
          headers,
        })
        .then((response) => {
          setShowPopup(true);
        })
        .catch((error) => {
          console.error("Error in creating appointment:", error);
        })
        .finally(() => {
          setIsLoading(false);
        });
    } else {
      keycloak.login()

    }
  };

  const handleDateChange = (date) => {
    setSelectedDate(date);
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
            <h2 className="text-xl font-semibold mb-2">
              Location Information
            </h2>
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
          <GoogleMap coordinates={[[40.85, 14.26]]} />
        </div>
      </div>
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



// import React, { useState } from "react";
// import GoogleMap from "../components/GoogleMap"; // Assuming you have a GoogleMap component
// import DatePicker from "react-datepicker";
// import "react-datepicker/dist/react-datepicker.css";
// import { useKeycloak } from "@react-keycloak/web";
// import { useLocation } from "react-router-dom";
// import axios from "axios";

// const AppointmentReservationPage = () => {
//   const location = useLocation();
//   const data = location.state;

//   const [selectedDate, setSelectedDate] = useState(new Date());
//   const [showPopup, setShowPopup] = useState(false);
//   const [isLoading, setIsLoading] = useState(false); // State for loading animation
//   const { keycloak } = useKeycloak();

//   const handleConfirmAppointment = () => {
//     if (keycloak.authenticated) {
//       const headers = {
//         Authorization: `Bearer ${keycloak.token}`
//       };
  
//       const requestData = {
//         startTimestamp: selectedDate.toISOString(), // Fill in your desired start timestamp
//         visitId: data.visitId, // Fill in visitId
//         patientEmail: keycloak.tokenParsed.email, // Fill in patient's email
//         doctorEmail: data.doctorEmail, // Fill in doctor's email
//         address: data.locationAddress // Fill in address
//       };


//       setIsLoading(true); // Set loading state to true when confirming appointment

//       axios.post("http://localhost:9080/api/appointment", requestData, {
//           headers,
//         })
//         .then((response) => {
//           setShowPopup(true);
//         })
//         .catch((error) => {
//           console.error("Error in creating appointment:", error);
//         })
//         .finally(() => {
//           setIsLoading(false); // Set loading state to false when appointment confirmation process is completed
//         });
//     } else {
//       keycloak.login({ redirectUri: "http://localhost:5173/appointments/reservation"});    }
//   };

//   const handleDateChange = (date) => {
//     setSelectedDate(date)
//   }

//   return (
//     <div className="container mx-auto p-8 bg-white bg-opacity-75 rounded-lg mt-20">
//       <div className="grid grid-cols-2 md:grid-cols-2 gap-6 mb-2">
//         <div>
//           <h1 className="text-3xl font-bold mb-4">Reserve Appointment</h1>
//           <div className="mb-6">
//             <h2 className="text-xl font-semibold mb-2">Visit Details</h2>
//             <p>
//               <span className="italic text-blue-600">Type:</span> {data.visitType}
//             </p>
//             <p>
//               <span className="italic text-blue-600">Price:</span> {data.visitPrice} €
//             </p>
//           </div>
//           {/* Date and Time Picker */}
//           <div className="mb-6">
//             <h2 className="text-xl text-gray font-semibold mb-2">
//               Select Date and Time
//             </h2>
//             <DatePicker
//               selected={selectedDate}
//               onChange={(date) => handleDateChange(date)}
//               showTimeSelect
//               dateFormat="Pp"
//               minDate={new Date()} // Disables past dates
//               maxDate={new Date(Date.now() + 30 * 24 * 60 * 60 * 1000)} // Disables dates ahead of 1 month
//               preventOpenOnFocus={true}
//               onKeyDown={(e) => {
//                 e.preventDefault();
//               }}
//             />
//           </div>
//           {/* Doctor Information */}
//           <div className="mb-4">
//           <div className="mb-4">
//             <h2 className="text-xl font-semibold mb-2">Doctor Information</h2>
//             <p>
//               <span className="italic text-blue-600">Name:</span>{" "}
//               {data.doctorName}
//             </p>
//             <p>
//               <span className="italic text-blue-600">Email:</span>{" "}
//               {data.doctorEmail}
//             </p>
//             <p>
//               <span className="italic text-blue-600">Phone:</span>{" "}
//               {data.doctorPhoneNumber}
//             </p>
//             </div>
//             <div className="mb-4">
//             <h2 className="text-xl font-semibold mb-2">Location Information</h2>
//             <p>
//               <span className="italic text-blue-600">Address:</span>{" "}
//               {data.locationAddress}
//             </p>
//             <p>
//               <span className="italic text-blue-600">Location:</span>{" "}
//               {data.locationName}
//             </p>
//             <p>
//               <span className="italic text-blue-600">Email:</span> {data.contactInfo.email}
//             </p>
//             <p>
//               <span className="italic text-blue-600">Phone:</span> {data.contactInfo.phoneNumber}
//             </p>
//           </div>
//           </div>
//           <button
//             className="bg-blue-500 text-white font-semibold px-4 py-2 mt-4 rounded-md hover:bg-blue-600"
//             onClick={handleConfirmAppointment}
//             disabled={isLoading} // Disable button when loading
//           >
//             {isLoading ? (
//               <div className="flex items-center">
//                 <span className="mr-2">Confirming...</span>
//                 <svg
//                   className="animate-spin h-5 w-5 text-white"
//                   xmlns="http://www.w3.org/2000/svg"
//                   fill="none"
//                   viewBox="0 0 24 24"
//                 >
//                   <circle
//                     className="opacity-25"
//                     cx="12"
//                     cy="12"
//                     r="10"
//                     stroke="currentColor"
//                     strokeWidth="4"
//                   ></circle>
//                   <path
//                     className="opacity-75"
//                     fill="currentColor"
//                     d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
//                   ></path>
//                 </svg>
//               </div>
//             ) : (
//               "Confirm Appointment"
//             )}
//           </button>
//         </div>
//         <div className="w-full h-full">
//           <GoogleMap coordinates={[[40.85, 14.26]]} />
//         </div>
//       </div>
//       {showPopup && (
//         <div className="fixed inset-0 flex items-center justify-center">
//           <div className="bg-white p-8 rounded-lg shadow-lg">
//             <p>
//               An email has been sent to {keycloak.tokenParsed.email} for further
//               information.
//             </p>
//             <p>Soon a doctor will accept the reservation.</p>

//             <div className="text-center">
//               {" "}
//               {/* Center the button */}
//               <button
//                 className="bg-blue-500 text-white font-semibold px-4 py-2 mt-4 rounded-md hover:bg-blue-600"
//                 onClick={() => setShowPopup(false)}
//               >
//                 Got it!
//               </button>
//             </div>
//           </div>
//         </div>
//       )}
//     </div>
//   );
// };

// export default AppointmentReservationPage;