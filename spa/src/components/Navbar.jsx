import React, { useState } from 'react';
import { AiOutlineClose, AiOutlineMenu } from 'react-icons/ai';
import { useKeycloak } from '@react-keycloak/web';
import { useNavigate } from 'react-router-dom'

const Navbar = () => {

  const navigate = useNavigate()
  const { keycloak } = useKeycloak();
  const isAuthenticated = keycloak.authenticated;

  // State to manage the navbar's visibility
  const [nav, setNav] = useState(false);

  // Toggle function to handle the navbar's display
  const handleNav = () => {
    setNav(!nav);
  };

  // Handle click on a navigation item
  const handleItemClick = (text) => {
    alert(`Clicked on ${text}`);
  };

  const handleLoginClick = () => {
    if(isAuthenticated)
      navigate('/home')
    else
      keycloak.login()
  }
  const handleRegisterClick = () => {
    keycloak.register()
  }
  const handleHomeClick = () => {
    navigate('/home')
  }

  // Array containing navigation items
  const navItems = [
    { id: 1, text: 'Home', onClick: handleHomeClick},
    { id: 2, text: 'Register',onClick: handleRegisterClick },
    { id: 3, text: 'Login', onClick: handleLoginClick},
    { id: 4, text: 'Contact',onClick: null },
  ];

  return (
      <div className='bg-blue-700 flex justify-between items-center h-24 mx-auto px-4 text-white'>
         {/* Logo */}
      <h1 className='w-full text-3xl font-bold text-white'>APPOINTMED</h1>

      {/* Desktop Navigation */}
      <ul className='hidden md:flex'>
        {navItems.map(item => (
          <li
            key={item.id}
            className='p-4 hover:bg-blue-500 rounded-xl m-2 cursor-pointer duration-300 hover:text-black'
            onClick={item.onClick} // Add onClick event
          >
            {item.text}
          </li>
        ))}
      </ul>

      {/* Mobile Navigation Icon */}
      <div onClick={handleNav} className='block md:hidden'>
        {nav ? <AiOutlineClose size={20} /> : <AiOutlineMenu size={20} />}
      </div>

      {/* Mobile Navigation Menu */}
      <ul
        className={
          nav
            ? 'fixed md:hidden left-0 top-0 w-[60%] h-full border-r border-r-white bg-blue-700 ease-in-out duration-500'
            : 'ease-in-out w-[60%] duration-500 fixed top-0 bottom-0 left-[-100%]'
        }
      >
        {/* Mobile Logo */}
        <h1 className='w-full text-3xl font-bold text-white m-4'>APPOINTMED</h1>

        {/* Mobile Navigation Items */}
        {navItems.map(item => (
          <li
            key={item.id}
            className='p-4 border-b rounded-xl hover:bg-blue-500 duration-300 hover:text-black cursor-pointer border-white'
            onClick={() => handleItemClick(item.text)} // Add onClick event
          >
            {item.text}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Navbar;
