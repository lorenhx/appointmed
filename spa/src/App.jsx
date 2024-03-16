import React from "react";
import Keycloak from "keycloak-js";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Appointments from "./pages/Appointments";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import "./Output.css";


function App() {
  const keycloak = new Keycloak({
    url: "http://localhost:8080",
    realm: "master",
    clientId: "oauth2-appointmed-spa",
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

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={initOptions}
      LoadingComponent={<div>Carico</div>}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <div className="bg-[url('/doctor1.jpg')] bg-cover h-screen">
          <Navbar />
          <Routes>
            <Route path="/" element={<Navbar />} />
            <Route path="/home" element={<Home />} />
            <Route path="/appointments" element={<Appointments />} />
          </Routes>
        </div>
      </Router>
    </ReactKeycloakProvider>
  );
}

export default App;
