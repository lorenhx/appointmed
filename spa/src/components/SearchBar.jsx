import React, { useState } from "react";
import Select from "react-tailwindcss-select";

const SearchBar = ({ options, primaryColor, isMultiple, placeholder, isSearchable }) => {
    const [selectedOption, setSelectedOption] = useState(null);

    const handleChange = (value) => {
        setSelectedOption(value);
    };

    return (
        <Select
            value={selectedOption}
            onChange={handleChange}
            options={options}
            primaryColor={primaryColor}
            isMultiple={isMultiple}
            placeholder={placeholder}
            isSearchable={isSearchable}
        />
    );
};

export default SearchBar;