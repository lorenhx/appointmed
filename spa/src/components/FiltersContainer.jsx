import React from "react";
import SearchBar from "./SearchBar";

const array = [
  { value: "ciao", label: "ciao" },
  { value: "ciao", label: "ciao" },
  { value: "ciao", label: "ciao" },
];

const FiltersContainer = () => {
  return (
    <div className="flex flex-col gap-5 h-max w-1/4 m-12 p-8 bg-gradient-to-b from-blue-600 to-blue-300">
      <h2 className="text-lg text-white  font-semibold mb-4">Filter your results</h2>{" "}
      <SearchBar
        options={array}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Accessibility"
        isSearchable={true}
      />
      <SearchBar
        options={array}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Payment Type"
        isSearchable={true}
      />
      <SearchBar
        options={array}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Languages spoken"
        isSearchable={true}
      />
      <SearchBar
        options={array}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="First visit availability"
        isSearchable={true}
      />
      <SearchBar
        options={array}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Visit type"
        isSearchable={true}
      />
      <SearchBar
        options={array}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Price"
        isSearchable={true}
      />
    </div>
  );
};

export default FiltersContainer;
