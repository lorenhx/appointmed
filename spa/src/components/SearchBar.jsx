import React, { useState } from "react";
import Select from "react-tailwindcss-select";

const SearchBar = ({ options, primaryColor, isMultiple, placeholder, isSearchable, handleSelectedValues }) => {
    const [selectedOption, setSelectedOption] = useState(null);

    const handleChange = (value) => {
        handleSelectedValues(value);
        setSelectedOption(value);
    };

    const optionsWithLabels = options.map(option => ({ value: option, label: option }));

    
    return (
        <Select
            value={selectedOption}
            onChange={handleChange}
            options={optionsWithLabels}
            primaryColor={primaryColor}
            isMultiple={isMultiple}
            placeholder={placeholder}
            isSearchable={isSearchable}
            classNames={{
                menuButton: ({ isDisabled }) => (
                    `flex text-gray-400 border border-gray-300 rounded shadow-sm transition-all duration-300 focus:outline-none ${
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

export default SearchBar;
