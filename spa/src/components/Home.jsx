import React from 'react';
import { useKeycloak } from '@react-keycloak/web';
import Private from './Private';


const Home = () => {
  const { keycloak } = useKeycloak();
  const isAuthenticated = keycloak.authenticated;

  const handleLogin = () => {
    keycloak.login();
  };

  return (
    <div>
      {isAuthenticated ? (
        <Private></Private>
      ) : (
        <div>
          <button onClick={handleLogin}>Login</button>
        </div>
      )}
    </div>
  );
};

export default Home;
