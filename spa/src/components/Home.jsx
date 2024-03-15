import React from "react";
import Navbar from "./Navbar";
import SearchBar from "./SearchBar";

const Home = () => {
  return (
    <div className="flex-col space-y-36">
      <Navbar></Navbar>
      <SearchBar></SearchBar>         
    </div>
  );
};

export default Home;
