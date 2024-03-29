import React from "react";
import SearchBar from "./SearchBar";
import SearchButton from "./SearchButton";


const FilterBarDoctorList = ({filters}) => {

  const { accessibilities, languages, paymentTypes, visitTypes } = filters


  return (
    <div className="flex flex-col gap-5 h-max w-1/4  p-8 bg-gradient-to-b from-blue-600 to-blue-300">
      <h2 className="text-lg text-white  font-semibold mb-4">Filter your results</h2>
      <SearchBar
        options={accessibilities}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Accessibility"
        isSearchable={true}
      />
      <SearchBar
        options={paymentTypes}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Payment Type"
        isSearchable={true}
      />
      <SearchBar
        options={languages}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Languages spoken"
        isSearchable={true}
      />
      <SearchBar
        options={visitTypes}
        primaryColor={"blue"}
        isMultiple={true}
        placeholder="Visit type"
        isSearchable={true}
      />
    <SearchButton onSearch={null}></SearchButton>
    </div>
  );
};

export default FilterBarDoctorList;
