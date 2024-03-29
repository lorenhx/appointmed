import React, { useState, useEffect } from "react";
import axios from "axios"; // Import Axios
import SearchBar from "../components/SearchBar";
import GoogleSearch from "../components/GoogleSearch";
import SearchButton from "../components/SearchButton";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();
  const [specializations, setSpecializations] = useState([]);
  const [selectedSpecializations, setSelectedSpecializations] = useState([]);
  const [selectedLocation, setSelectedLocation] = useState();
  const [searchRange, setSearchRange] = useState(100); // Default range value is 100 km

  useEffect(() => {
    axios.get(`${import.meta.env.VITE_API_URL}/doctor/specialization`)
      .then(response => {
        setSpecializations(response.data);
      })
      .catch(error => {
        alert("Error fetching specializations, try again later.");
      });
  }, []);
  
  const onSearch = () => {
    axios.get(`${import.meta.env.VITE_API_URL}/doctor?specializations=${selectedSpecializations.join("&specializations=")}&location=${selectedLocation}&range=${searchRange}`)
      .then(response => {
        const data = { location: selectedLocation, filters: response.data.filters, doctors: response.data.doctors };
        navigate("/doctor-list", { state: data });
      })
      .catch(error => {
        alert("Error fetching doctors, try again later.");
      });
  };

  const handleSelectedSpecializations = (specializations) => {
    setSelectedSpecializations(specializations.map(item => item.value))
  }

  const handleSelectedLocation = (location) => {
    setSelectedLocation(location)
  }

  const handleRangeChange = (event) => {
    setSearchRange(event.target.value);
  };

  return (
    <div className="mt-20 flex justify-start">
      <div className="bg-gradient-to-r from-blue-300 to-blue-500 w-1/2 py-6 rounded-lg ml-16">
        <div className="flex justify-center">
          <div className="w-1/4 mr-2">
            <SearchBar
              options={specializations}
              primaryColor={"blue"}
              isMultiple={true}
              placeholder="Specializations"
              isSearchable={true}
              className="mb-5"
              handleSelectedValues={handleSelectedSpecializations}
            />
          </div>
          <div className="w-1/2 mr-2">
            <GoogleSearch handleSelectedLocation={handleSelectedLocation}/>
          </div>
          <div className="flex flex-col  mr-2 text-white">
            <input
              type="range"
              min="1"
              max="150"
              value={searchRange}
              onChange={handleRangeChange}
            />
            <p>Search Range: {searchRange} km</p>
          </div>
        </div>
        <div className="flex justify-center"> 
          <SearchButton onSearch={onSearch} /> 
        </div>
      </div>
    </div>
  );
};

export default Home;
