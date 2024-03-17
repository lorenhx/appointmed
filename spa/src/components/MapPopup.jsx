import React, { useState } from "react";
import GoogleMap from "./GoogleMap";

const MapPopup = ({ coordinates, onClose }) => {



  return (
    <div className="fixed top-0 left-0 w-screen h-screen bg-black bg-opacity-50 flex justify-center items-center">
      <div className="bg-white p-4 rounded-lg">
        <div className="text-center font-semibold mb-4">Doctor locations</div>
        <div style={{ width: "700px", height: "600px" }}>
          <GoogleMap coordinates={coordinates} />
        </div>
        <div className="flex justify-center">
          <button
            onClick={onClose}
            className="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default MapPopup;