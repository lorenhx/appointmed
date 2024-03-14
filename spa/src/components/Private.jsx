import { useKeycloak } from '@react-keycloak/web';



const Private = () => {
    const { keycloak } = useKeycloak();
    const isAuthenticated = keycloak.authenticated;

    const handleLogout = () => {
        keycloak.logout();
      };

    return (
      <div>
        {
       isAuthenticated ? (
            <div>
            <h2>Welcome to the Secret Page! Here is your jwt access token you can use to access appointmed api: {keycloak.token}</h2>
            <button onClick={handleLogout}>Logout</button>
            </div>
          ) : (
            keycloak.login()
          )
      }
      </div>
    );
  };

export default Private;