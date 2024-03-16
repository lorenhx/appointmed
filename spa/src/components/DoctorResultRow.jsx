import React from "react";
import ReviewContainer from "./ReviewContainer";
import List from "./List";

const DoctorResultRow = ({ reviewData, locations, locationsLabel, visitsLabel, strings, onItemClick }) => {
  return (
    <div className="flex">
      <div className="w-1/5">
        <ReviewContainer {...reviewData} />
      </div>
      <div className="w-2/5">
      <List items={locations} label={locationsLabel} onItemClick={onItemClick} />
      </div>
      <div className="w-2/5">
        <List items={strings} label={visitsLabel} onItemClick={onItemClick} />
      </div>
    </div>
  );
};

export default DoctorResultRow;
