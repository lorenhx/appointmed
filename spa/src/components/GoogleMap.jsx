import React, { useEffect, useState } from "react";
import GoogleMapReact from "google-map-react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
import { geocode, RequestType, setKey} from "react-geocode";

const Marker = ({ lat, lng }) => (
  <FontAwesomeIcon
    icon={faLocationDot}
    className="text-red-700 cursor-pointer hover:text-blue-500 text-3xl"
    lat={lat}
    lng={lng}
  />
);
setKey(import.meta.env.VITE_GOOGLE_API_KEY); 
export default function GoogleMap({ center, locations }) {
  const [mapCenter, setMapCenter] = useState();
  const [markers, setMarkers] = useState([]);

  useEffect(() => {
    const geocodeAddresses = async () => {
      console.log(locations)
      try {
        const markers = [];
        for (const address of locations) {
          const response = await geocode(RequestType.ADDRESS, address);
          const { results } = response;
          const { lat, lng } = results[0].geometry.location;
          markers.push({ lat, lng });
        }
        setMarkers(markers);
      } catch (error) {
        console.error("Error geocoding addresses:", error);
      }
    };

    if (locations && locations.length > 0) {
      geocodeAddresses();
    }
  }, [locations]);

  useEffect(() => {
    const getLocationCoordinates = async () => {
      try {
        const response = await geocode(RequestType.ADDRESS, center);
        const { results } = response;
        const { lat, lng } = results[0].geometry.location;
        setMapCenter({ lat, lng });
      } catch (error) {
        console.error("Error geocoding center:", error);
      }
    };
    if (center) {
      getLocationCoordinates();
    }
  }, [center]);

  return (
      <GoogleMapReact
        bootstrapURLKeys={{
          key: `${import.meta.env.VITE_GOOGLE_API_KEY}`,
        }}
        center={mapCenter}
        zoom={11}
      >
        {/* Render markers for each location */}
        {markers.map((coordinate, index) => (
          <Marker key={index} lat={coordinate.lat} lng={coordinate.lng} />
        ))}
      </GoogleMapReact>
  );
}
