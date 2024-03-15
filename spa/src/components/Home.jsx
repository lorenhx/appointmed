import React from "react";
import Navbar from "./Navbar";
import SearchBar from "./SearchBar";
import GoogleSearch from "./GoogleSearch";
import SearchButton from "./SearchButton";

const Home = () => {

  return (
    <div className="space-y-36">
      <Navbar></Navbar>
      <div className="justify-center">
        <div className="flex space-x-20">
          <SearchBar></SearchBar>
          <GoogleSearch></GoogleSearch>
          <SearchButton></SearchButton>
        </div>
      </div>
    </div>
  );
};

export default Home;
