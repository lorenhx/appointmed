import { useEffect, useState } from "react";
import Select from "react-tailwindcss-select";

import useGoogle from "react-google-autocomplete/lib/usePlacesAutocompleteService";

const GoogleSearch = ({handleSelectedLocation}) => {
  const [text, setText] = useState("");
  const [locations, setLocations] = useState([]);

  const { placePredictions, getPlacePredictions, isPlacePredictionsLoading } =
    useGoogle({
      apiKey: `${import.meta.env.VITE_GOOGLE_API_KEY}`,
      sessionToken: false,
      debounce: "500",
    });

  useEffect(() => {
    if (!isPlacePredictionsLoading && placePredictions) {
      let predictions = placePredictions.map((result) => ({
        value: result.description,
        label: `📍 ${result.description}`,
      }));
      setLocations(predictions);
    }
  }, [isPlacePredictionsLoading]);

  return (
    <Select
      value={text}
      onSearchInputChange={(e) => {
        getPlacePredictions({ input: e.target.value });
        setText(e.target.value);
      }}
      onChange={(value) => {
        handleSelectedLocation(value.value)
        setText(value);
      }}
      options={locations}
      primaryColor={"blue"}
      placeholder="Select Location"
      isSearchable={true}
      loading={isPlacePredictionsLoading}
      noOptionsMessage={"No Locations found"}
      classNames={{
        menuButton: ({ isDisabled }) => (
            `flex text-gray-400 border border-gray-300 rounded shadow-sm overflow-x-auto transition-all duration-300 focus:outline-none ${
                isDisabled
                    ? "bg-gray-200"
                    : "bg-white hover:border-gray-400 focus:border-blue-500 focus:ring focus:ring-blue-500/20"
            }`
        ),
        menu: "absolute z-10 w-full bg-white shadow-lg border rounded py-1 mt-1.5 text-sm text-gray-700",
        listItem: ({ isSelected }) => (
            `block transition duration-200 px-2 py-2 cursor-pointer select-none truncate rounded ${
                isSelected
                    ? `text-white bg-blue-500`
                    : `text-black hover:bg-blue-100`
            }`
        )
    }}
    />
  );
};

export default GoogleSearch;
