import React, { useState } from "react";
import SearchBar from "./SearchBar";
import DatePicker from "react-datepicker";
import SearchButton from "./SearchButton";
import "react-datepicker/dist/react-datepicker.css";

const FilterBar = ({ handleFilterChange }) => {
  const [status, setStatus] = useState("");
  const [email, setEmail] = useState("");
  const [visitType, setVisitType] = useState("");
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  const options = [
    { value: "PENDING", label: "PENDING ⏳" },
    { value: "CONFIRMED", label: "CONFIRMED ✔️" },
    { value: "REJECTED", label: "REJECTED ❌" },
  ];

  const handleStatusChange = (value) => {
    setStatus(value);
    handleFilterChange({ status: value });
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
    handleFilterChange({ email: e.target.value });
  };

  const handleVisitTypeChange = (value) => {
    setVisitType(value);
    handleFilterChange({ visitType: value });
  };

  const handleStartDateChange = (date) => {
    setStartDate(date);
    handleFilterChange({ startDate: date });
  };

  const handleEndDateChange = (date) => {
    setEndDate(date);
    handleFilterChange({ endDate: date });
  };

  return (
    <div className="bg-blue-300 p-4 rounded-md shadow-md h-max">
      <h2 className="text-lg text-white font-semibold mb-4">
        Filter your results
      </h2>
      <div className="flex flex-col gap-4">
        <SearchBar
          options={options}
          primaryColor="#4F46E5"
          placeholder="Filter by status"
          onChange={handleStatusChange}
          isSearchable={false}
        />
        <input
          type="text"
          placeholder="Filter by user email"
          value={email}
          onChange={handleEmailChange}
          className="border rounded-md p-2"
        />
        <SearchBar
          options={options}
          primaryColor="#4F46E5"
          placeholder="Filter by type"
          onChange={handleVisitTypeChange}
          isSearchable={false}
        />
        <DatePicker
          selected={startDate}
          onChange={handleStartDateChange}
          showTimeSelect
          dateFormat="Pp"
          minDate={new Date()}
          maxDate={endDate || new Date(Date.now() + 30 * 24 * 60 * 60 * 1000)}
          preventOpenOnFocus={true}
          onKeyDown={(e) => {
            e.preventDefault();
          }}
          className="border rounded-md p-2"
          wrapperClassName="border rounded-md p-2"
          calendarClassName="text-lg"
          placeholderText="Select start timestamp"
        />
        <DatePicker
          selected={endDate}
          onChange={handleEndDateChange}
          showTimeSelect
          dateFormat="Pp"
          minDate={startDate}
          maxDate={new Date(Date.now() + 30 * 24 * 60 * 60 * 1000)}
          preventOpenOnFocus={true}
          onKeyDown={(e) => {
            e.preventDefault();
          }}
          className="border rounded-md p-2"
          wrapperClassName="border rounded-md p-2"
          calendarClassName="text-lg"
          placeholderText="Select end timestamp"
        />
        <SearchButton onSearch={null}></SearchButton>
      </div>
    </div>
  );
};

export default FilterBar;
