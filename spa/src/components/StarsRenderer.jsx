import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

const StarsRenderer = ({reviews}) => {

  if(reviews===null)
    reviews = []
  const maxStars = 5;

  const totalStars = reviews.reduce((acc, review) => {
    const stars = review.stars === "FIVE" ? 5 :
                  review.stars === "FOUR" ? 4 :
                  review.stars === "THREE" ? 3 :
                  review.stars === "TWO" ? 2 :
                  review.stars === "ONE" ? 1 : 0;
    return acc + stars;
  }, 0);

  const averageRating = reviews.length > 0 ? totalStars / reviews.length : 0;

  const filledStars = Math.min(Math.round(averageRating), maxStars);

  const stars = [];
  for (let i = 0; i < maxStars; i++) {
    stars.push(
      <span key={i}>
        <FontAwesomeIcon
          icon={faStar}
          className={i < filledStars ? "text-yellow-400" : "text-gray-400"}
        />
      </span>
    );
  }
  return stars;
};

export default StarsRenderer;
