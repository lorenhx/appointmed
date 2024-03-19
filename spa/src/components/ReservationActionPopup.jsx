import React, { useState } from "react";

const ReservationActionPopup = ({ action, onClose, onSubmit }) => {
  const [notes, setNotes] = useState("");

  const handleSubmit = () => {
    onSubmit(notes);
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-8 rounded-lg shadow-lg">
        <h2 className="text-2xl font-semibold mb-4">
          {action === "confirm" ? "Confirm Reservation" : "Reject Reservation"}
        </h2>
        <textarea
          value={notes}
          onChange={(e) => setNotes(e.target.value)}
          placeholder="Enter notes..."
          className="w-full h-32 px-4 py-2 border border-gray-300 rounded-md mb-4 focus:outline-none focus:border-blue-500"
        />
        <button
          onClick={handleSubmit}
          className="bg-blue-500 text-white font-semibold px-6 py-3 rounded-md mr-4 hover:bg-blue-600"
        >
          Submit
        </button>
        <button
          onClick={onClose}
          className="bg-gray-500 text-white font-semibold px-6 py-3 rounded-md hover:bg-gray-600"
        >
          Cancel
        </button>
      </div>
    </div>
  );
};

export default ReservationActionPopup;
