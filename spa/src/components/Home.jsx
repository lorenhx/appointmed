import React from "react";
import Navbar from "./Navbar";
import SearchBar from "./SearchBar";
import GoogleSearch from "./GoogleSearch";
import SearchButton from "./SearchButton";
import doctor3 from "../../public/doctor3.jpg"; // Tell webpack this JS file uses this image

const Home = () => {
  return (
    <div className="bg-[url('/doctor1.jpg')] bg-cover h-screen">
      <Navbar />
      <div className="mt-20 flex justify-start">
        <div className="bg-gradient-to-r from-blue-300 to-blue-500 w-1/2 py-6 rounded-lg ml-16">
          <div className="flex justify-center">
            <div className="w-1/4 mr-2">
              {" "}
              <SearchBar />
            </div>
            <div className="w-1/2 mr-2">
              <GoogleSearch />
            </div>
          </div>
          <div className="flex justify-center mt-4">
            <div className="max-w-md">
              <SearchButton />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
