import React, { memo } from "react";

const DoctorProfileImage = ({ imageLink }) => {
  return (
    <div className="flex-shrink-0 h-16 w-16">
      <img
        className="h-full w-full rounded-full"
        src={imageLink}
        alt="Doctor Image here"
      />
    </div>
  );
};

export default memo(DoctorProfileImage);
