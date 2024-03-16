import React from "react";
import Navbar from "../components/Navbar";
import FiltersContainer from "../components/FiltersContainer";
import DoctorResultsRowContainer from "../components/DoctorResultsRowContainer";

const array = [
  "Via antonio 1",
  "via caspita2",
  "aaaaaaaaaaaaaaa",
  "Via antonio 1",
  "via caspita2",
  "aaaaaaaaaaaaaaa",
  "Via antonio 1",
  "via caspita2",
  "aaaaaaaaaaaaaaa"
];

const array1 = ["Visita1  50$", "Visita2  100$", "Visita3  350$"];

const reviewData = {
  name: "Johnny Bravo",
  rating: 4,
  profilePhoto: "/profile.jpg",
  mainSpecialization: "Cardiologist",
};

const Appointments = () => {
  const doctorData = [
    {
      reviewData: reviewData,
      locations: array,
      strings: array1,
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: reviewData,
      locations: array,
      strings: array1,
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: reviewData,
      locations: array,
      strings: array1,
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: reviewData,
      locations: array,
      strings: array1,
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    
  ];

  return (
    <div className="bg-[url('/doctor1.jpg')] bg-cover h-screen max-h-screen overflow-y-auto">
      <Navbar />
      <div className="container mx-auto flex space-x-5">
        <FiltersContainer />
        <DoctorResultsRowContainer doctorData={doctorData} />
      </div>
    </div>
  );
};

export default Appointments;
