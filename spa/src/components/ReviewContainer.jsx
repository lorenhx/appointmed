import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

const ReviewContainer = ({
  name,
  rating,
  profilePhoto,
  mainSpecialization,
}) => {
  const renderStars = (rating) => {
    const stars = [];
    for (let i = 0; i < 5; i++) {
      stars.push(
        <span key={i}>
          <FontAwesomeIcon
            icon={faStar}
            className={i < rating ? "text-yellow-400" : "text-gray-400"}
          />
        </span>
      );
    }
    return stars;
  };

  return (
    <div className="bg-white max-h-36 p-4 shadow-md flex items-center">
      <div className="w-1/3">
        <img
          src={profilePhoto}
          alt="Profile"
          className="rounded-full w-16 h-16"
        />
      </div>
      <div className="w-2/3 ml-4">
        <h2 className="text-lg font-semibold">{name}</h2>
        <div className="flex flex-wrap mt-1 italic text-gray-500">
          <p className="mr-2">xxx</p>
          <p className="mr-2">{mainSpecialization}</p>
        </div>
        <div className="flex mt-1">{renderStars(rating)}</div>
        <div className="flex flex-wrap mt-1 italic text-gray-500">
        </div>
      </div>
    </div>
  );
};

export default ReviewContainer;
