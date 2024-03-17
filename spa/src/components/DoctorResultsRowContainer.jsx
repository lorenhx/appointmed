import React from "react";
import DoctorResultRow from "./DoctorResultRow";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMapLocation } from "@fortawesome/free-solid-svg-icons";

const DoctorResultsRowContainer = ({ doctorData, onMapLocationClick }) => {
  return (
    <div className="w-4/5 p-12">
      <h1 className="text-lg text-Gray font-semibold mb-4">
        Show locations on map{" "}
        <FontAwesomeIcon
          icon={faMapLocation}
          className="text-blue-700 cursor-pointer hover:text-blue-500"
          onClick={onMapLocationClick}
        />
      </h1>
      <div className="container mx-auto space-y-5">
        {doctorData.map((doctor, index) => (
          <DoctorResultRow
            key={index}
            reviewData={doctor.reviewData}
            locations={doctor.locations}
            strings={doctor.strings}
            locationsLabel={doctor.locationsLabel}
            visitsLabel={doctor.visitsLabel}
            onItemClick={doctor.onItemClick}
          />
        ))}
      </div>
    </div>
  );
};

export default DoctorResultsRowContainer;
