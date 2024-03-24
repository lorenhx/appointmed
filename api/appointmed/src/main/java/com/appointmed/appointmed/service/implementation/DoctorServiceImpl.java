package com.appointmed.appointmed.service.implementation;

import com.appointmed.appointmed.constant.Specialization;
import com.appointmed.appointmed.model.Doctor;
import com.appointmed.appointmed.model.Location;
import com.appointmed.appointmed.repository.DoctorRepository;
import com.appointmed.appointmed.service.DoctorService;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final GeoApiContext geoApiContext;
    private final Keycloak keycloak;

    @Override
    public List<Doctor> getDoctorsBySpecializations(List<Specialization> specializations) {
        return doctorRepository.findBySpecializationsIn(specializations);
    }

    @Override
    public List<Doctor> getDoctorsBySpecializationsAndLocationInRange(List<Specialization> specializations, String locationAddress, int range) {
        List<Doctor> doctorsFilteredBySpecialization = getDoctorsBySpecializations(specializations);

        try {
            LatLng locationCoordinates = getLocationCoordinates(locationAddress);

            List<Doctor> doctorsInRange = new ArrayList<>();

            for (Doctor doctor : doctorsFilteredBySpecialization) {
                List<Location> locationsInRange = new ArrayList<>();

                for (Location location : doctor.getLocations()) {

                    LatLng targetLocationCoordinates = getLocationCoordinates(location.getAddress());

                    double distance = calculateDistance(locationCoordinates.lat, locationCoordinates.lng,
                            targetLocationCoordinates.lat, targetLocationCoordinates.lng);

                    if (distance <= range) {
                        locationsInRange.add(location);
                    }
                }

                if (!locationsInRange.isEmpty()) {
                    Doctor doctorWithLocationsInRange = new Doctor(doctor.getEmail(), doctor.getReviews(),
                            doctor.getSpecializations(), locationsInRange, doctor.getAppointments());
                    doctorsInRange.add(doctorWithLocationsInRange);
                }
            }

            return doctorsInRange;
        } catch (ApiException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    private UserRepresentation getUserFromKeycloak(String doctorEmail) {
        RealmResource realmResource = keycloak.realm("master");
        UsersResource usersResource = realmResource.users();
        List<UserRepresentation> users = usersResource.searchByEmail(doctorEmail, true);
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    private LatLng getLocationCoordinates(String locationAddress) throws ApiException, InterruptedException, IOException {
        GeocodingResult[] locationResults = GeocodingApi.geocode(geoApiContext, locationAddress).await();
        return locationResults[0].geometry.location;
    }

    private double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    private double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));
        int EARTH_RADIUS = 6371;

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
