import React from "react";
import Keycloak from "keycloak-js";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Appointments from "./pages/Appointments";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import "./Output.css";
import AppointmentReservationPage from "./pages/AppointmentReservationPage";
import AppointmentTable from './components/AppointmentTable'
import AppointmentList from './components/AppointmentList'

function App() {
  const keycloak = new Keycloak({
    url: "http://localhost:8080",
    realm: "appointmed",
    clientId: "oauth2-appointmed",
  });
  const initOptions = { pkceMethod: "S256" };

  const handleOnEvent = async (event, error) => {
    if (event === "onAuthSuccess") {
      if (keycloak.authenticated) {
        console.log(keycloak.token);
      }
    } else {
      console.log(event);
    }
  };

  const doctorData = [
    {
      reviewData: {
        name: "Johnny Bravo",
        rating: 4,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Cardiologist",
        reviewCount: 73,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Andrea Rossi",
        rating: 5,
        profilePhoto: "/doctor3.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 20,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Antonio Russo",
        rating: 2,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 17,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Antonio Russo",
        rating: 2,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 17,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Antonio Russo",
        rating: 2,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 17,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Antonio Russo",
        rating: 2,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 17,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    

    // Add more objects as needed...
  ];

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={initOptions}
      LoadingComponent={<div>Carico</div>}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <div className="bg-[url('/doctor1.jpg')] bg-cover h-full">
          <Navbar />
          <Routes>
            <Route
              path="/appointments/reservation"
              element={<AppointmentReservationPage
                visitName="Dental Checkup"
                visitPrice="$50"
                doctorName="Dr. John Doe"
                doctorLocation="123 Main Street"
                doctorEmail="doctor@example.com"
                phone="+123 456 7890"
                coordinates={[[40.85, 14.26]]}
              />}/>
            <Route path="/home" element={<Home />} />
            <Route path="/appointments" element={<AppointmentTable doctorData={doctorData}/>} />
            <Route path="/manage" element={<AppointmentList/>} />
          </Routes>
        </div>
      </Router>
    </ReactKeycloakProvider>
  );
}

export default App;
