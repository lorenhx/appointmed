import React from 'react'
import Keycloak from 'keycloak-js'
import Home from './components/Home'
import Navbar from './components/Navbar'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { ReactKeycloakProvider } from '@react-keycloak/web'
import './Output.css'

function App() {

  const keycloak = new Keycloak({
    url: "http://localhost:9080",
    realm: "master",
    clientId: "oauth2-appointmed-spa"
  })
  const initOptions = { pkceMethod: 'S256' }

  const handleOnEvent = async (event, error) => {
    if (event === 'onAuthSuccess') {
      if (keycloak.authenticated) {
        console.log(keycloak.token)
      }
    }
    else{
      console.log(event)
    }
  }

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
      initOptions={initOptions}
      LoadingComponent={<div>Carico</div>}
      onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <Routes>
          <Route path='/' element={<Navbar />} />
          <Route path='/home' element={<div><Home /></div>} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </Router>
    </ReactKeycloakProvider>
  )
}

export default App




// 891104114207-j7ae61qh56r1n62afm8tlnq08lattueb.apps.googleusercontent.com

// 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