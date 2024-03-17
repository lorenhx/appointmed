import React from "react";
import FiltersContainer from "../components/FiltersContainer";
import DoctorResultsRowContainer from "../components/DoctorResultsRowContainer";
import MapPopup from "../components/MapPopup";
import { useState } from "react";

const Appointments = () => {
  const [isMapPopupOpen, setIsMapPopupOpen] = useState(false);

  const doctorData = [
    {
      reviewData: {
        name: "Johnny Bravo",
        rating: 4,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Cardiologist",
        reviewCount: 73,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Andrea Rossi",
        rating: 5,
        profilePhoto: "/doctor3.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 20,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Antonio Russo",
        rating: 2,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 17,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },
    {
      reviewData: {
        name: "Antonio Russo",
        rating: 2,
        profilePhoto: "/profile.jpg",
        mainSpecialization: "Nutritionist",
        reviewCount: 17,
      },
      locations: [
        { location: "Via antonio 1", coordinates: [40.85, 14.26] },
        { location: "via caspita2", coordinates: [40.852, 14.261] },
        { location: "aaaaaaaaaaaaaaa", coordinates: [40.849, 14.259] },
      ],
      strings: ["Visita1  50$", "Visita2  100$", "Visita3  350$"],
      locationsLabel: "Select location",
      visitsLabel: "Select visit",
      onItemClick: null,
    },

    // Add more objects as needed...
  ];

  const allCoordinates = doctorData.flatMap((doctor) =>
    doctor.locations.map((location) => location.coordinates)
  );

  const onMapLocationClick = () => {
    setIsMapPopupOpen(true);
  };

  const closeMapPopup = () => {
    setIsMapPopupOpen(false);
  };

  return (
    <div className="container mx-auto flex">
      <FiltersContainer />
      <DoctorResultsRowContainer
        doctorData={doctorData}
        onMapLocationClick={onMapLocationClick}
      />
      {isMapPopupOpen && (
        <MapPopup coordinates={allCoordinates} onClose={closeMapPopup} />
      )}
    </div>
  );
};

export default Appointments;
