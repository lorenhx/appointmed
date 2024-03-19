import React from "react";
import GoogleMapReact from "google-map-react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";

const Marker = ({ lat, lng }) => (
  <FontAwesomeIcon
    icon={faLocationDot}
    className="text-red-700 cursor-pointer hover:text-blue-500 text-3xl"
    lat={lat}
    lng={lng}
  />
);

export default function GoogleMap({ center, coordinates }) {
  const defaultProps = {
    center: {
      lat: 40.85,
      lng: 14.26,
    },
    zoom: 14,
  };

  return (
      <GoogleMapReact
        bootstrapURLKeys={{
          key: "",
        }}
        defaultCenter={center || {
          lat: 40.85,
          lng: 14.26,
        }}
        defaultZoom={defaultProps.zoom}
      >
        {coordinates.map((coordinate, index) => (
          <Marker key={index} lat={coordinate[0]} lng={coordinate[1]} />
        ))}
      </GoogleMapReact>
  );
}
