import React from "react";
import Keycloak from "keycloak-js";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import DoctorList from "./pages/DoctorList";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import "./Output.css";
import AppointmentReservation from "./pages/AppointmentReservation";
import AppointmentManage from "./pages/AppointmentManage";

function App() {
  const keycloak = new Keycloak({
    url: `${import.meta.env.VITE_KEYCLOAK_URL}`,
    realm: `${import.meta.env.VITE_KEYCLOAK_REALM}`,
    clientId: `${import.meta.env.VITE_KEYCLOAK_CLIENT_ID}`,
  });

  console.log(import.meta.env.VITE_KEYCLOAK_URL)
  console.log(import.meta.env.VITE_KEYCLOAK_REALM)
  console.log(import.meta.env.VITE_KEYCLOAK_CLIENT_ID)

  const initOptions = { pkceMethod: "S256" };

  const handleOnEvent = async (event, error) => {
    if (event === "onAuthSuccess") {
      if (keycloak.authenticated) {
        console.log(keycloak.token);
      }
    } else {
    }
  };

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={initOptions}
      LoadingComponent={<div>Carico</div>}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <div className="bg-[url('/background.jpg')] bg-cover h-screen">
          <Navbar />
          <Routes>
            <Route
              path="/appointment/reservation"
              element={<AppointmentReservation />}
            />
            <Route path="/doctor-list" element={<DoctorList />} />
            <Route path="/home" element={<Home />} />
            <Route path="/appointment/manage" element={<AppointmentManage />} />
            <Route path="*" element={<Home />} />
          </Routes>
        </div>
      </Router>
    </ReactKeycloakProvider>
  );
}

export default App;
