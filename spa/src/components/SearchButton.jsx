import { FaSearchengin } from "react-icons/fa";
import { useState } from "react";

const SearchButton = () => {
  const [isSpinning, setIsSpinning] = useState(false);

  const handleClick = () => {
    setIsSpinning(true);
    setTimeout(() => {
      setIsSpinning(false);
    }, 3000);
  };

  return(
  <button
    className="bg-blue-700 hover:bg-blue-500 text-white font-bold py-2 px-4 rounded-lg inline-flex items-center"
    onClick={handleClick}
  >
    <svg
      className={`${isSpinning ? "animate-spin" : "animate-none"} h-5 w-5 mr-3`}
      viewBox="0 0 24 24"
    >
      <FaSearchengin size="1.5rem" />
    </svg>
    <span>Search</span>
  </button>
  );
};

export default SearchButton;
