import { useEffect, useState } from "react";
import Select from "react-tailwindcss-select";

import useGoogle from "react-google-autocomplete/lib/usePlacesAutocompleteService";

const GoogleSearch = () => {
  const [text, setText] = useState("");
  const [locations, setLocations] = useState([]);

  const { placePredictions, getPlacePredictions, isPlacePredictionsLoading } =
    useGoogle({
      apiKey: "",
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
      options={locations}
      primaryColor={"blue"}
      placeholder="Select Location"
      isSearchable={true}
    />
  );
};

export default GoogleSearch;
