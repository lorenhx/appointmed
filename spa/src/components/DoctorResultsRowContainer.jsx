import React from "react";
import DoctorResultRow from "./DoctorResultRow";

const DoctorResultsRowContainer = ({ doctorData }) => {
  return (
    <div className="w-4/5 p-12">
      <h2 className="text-lg text-Black  font-semibold mb-4">
        Reserve an appointment with a doctor
      </h2>
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
