import React, { useState } from 'react';

const SearchBar = ({ onSearch }) => {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearch = () => {
    // Call the onSearch callback with the current search term
    onSearch(searchTerm);
  };

  const handleChange = (e) => {
    // Update the search term state as the user types
    setSearchTerm(e.target.value);
  };

  return (
    <div className="relative">
      <input
        type="text"
        placeholder="Search..."
        className="border border-gray-300 rounded-md px-4 py-2 focus:outline-none focus:ring focus:ring-blue-400 w-full"
        value={searchTerm}
        onChange={handleChange}
      />
      <button
        className="absolute inset-y-0 right-0 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:bg-blue-600"
        onClick={handleSearch}
      >
        Search
      </button>
    </div>
  );
};

export default SearchBar;
