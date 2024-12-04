package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.*;
import com.cfctechnology.travel.model.enum_model.EBookingStatus;
import com.cfctechnology.travel.repository.BookingRepository;
import com.cfctechnology.travel.repository.DestinationRepository;
import com.cfctechnology.travel.repository.TourCategoryRepository;
import com.cfctechnology.travel.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    @Autowired
    private  TourRepository tourRepository;
    @Autowired
    private  DestinationRepository destinationRepository;
    @Autowired
    private  BookingRepository bookingRepository;
    @Autowired
    private  TourCategoryRepository tourCategoryRepository;

    public Tour getTourById(long id) {
        Optional<Tour> tour = tourRepository.findById(id);
        return tour.orElse(null);
    }

    public PageResult<Tour> getPageTours(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<Tour> tours;
        if (name != null && !name.isEmpty()) {
            tours = tourRepository.findByNameContaining(name, pageable);
        } else {
            tours = tourRepository.findAll(pageable);
        }
        return new PageResult<>(tours.getContent(), tours.getTotalPages());
    }

    public PageResult<Tour> searchTours(int page, int size, String name, Double minPrice, Double maxPrice, LocalDate startDate ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tour> tours;
        tours = tourRepository.searchTours(name, minPrice, maxPrice, startDate, pageable);
        return new PageResult<>(tours.getContent(), tours.getTotalPages());
    }

    public List<Tour> geTourByDestinationId(long destinationId){

        List<Tour> destinations;

            destinations = tourRepository.findToursByDestinationId(destinationId);

        return destinations;
    }


    public Tour createTour(Tour tour, long destinationId) {
        Optional<Destination> destination = destinationRepository.findById(destinationId);
        if(destination.isPresent()) {
            tour.setDestination(destination.get());
            return tourRepository.save(tour);
        }
        return null;
    }
    public Tour updateTour(Tour tour, long destinationId ,long id ) {
        Optional<Tour> tourOptional = tourRepository.findById(id);
        Optional<Destination> destination = destinationRepository.findById(destinationId);
        if (tourOptional.isPresent()) {
            if(destination.isPresent()) {
                tourOptional.get().setName(tour.getName());
                tourOptional.get().setPrice(tour.getPrice());
                tourOptional.get().setStartDate(tour.getStartDate());
                tourOptional.get().setEndDate(tour.getEndDate());
                tourOptional.get().setDescriptionTour(tour.getDescriptionTour());
                tourOptional.get().setDestination( destination.get());
                return tourRepository.save(tourOptional.get());
            }

        }
        return null;
    }

    public void deleteTour(long id) {
       Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent() ) {
            List<Booking> bookings = tour.get().getBookings();
            for (Booking booking : bookings) {
                booking.setStatus(EBookingStatus.DELETED);
                booking.setTour(null);
            }
            List<TourCategory> tourCategories = tour.get().getTourCategories();
            for(TourCategory tourCategory : tourCategories) {
                tourCategory.setTour(null);
            }
            tourRepository.deleteById(id);
        }
    }
}
