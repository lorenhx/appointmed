import React from "react";
import Navbar from "../components/Navbar";
import SearchBar from "../components/SearchBar";
import GoogleSearch from "../components/GoogleSearch";
import SearchButton from "../components/SearchButton";
import { useNavigate } from "react-router-dom";

const specializations = [
  { value: "Cardiologist", label: "🫀 Cardiologist" },
  { value: "Nutritionist", label: "🥗 Nutritionist" },
  { value: "Neurologist", label: "🧠 Neurologist" },
  { value: "Ent", label: "👂🏼 Ent" },
  { value: "Ophthalmologist", label: "👓 Ophthalmologist" },
];

const Home = () => {
  const navigate = useNavigate();

  const onSearch = () => {
    navigate("/appointments");
  };
  return (
    <div className="bg-[url('/doctor1.jpg')] bg-cover h-screen">
      <Navbar />
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
              />
            </div>
            <div className="w-1/2 mr-2">
              <GoogleSearch />
            </div>
          </div>
          <div className="flex justify-center mt-4">
            <div className="max-w-md">
              <SearchButton onSearch={onSearch} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
