package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.*;
import com.cfctechnology.travel.repository.BookingRepository;
import com.cfctechnology.travel.repository.DestinationRepository;
import com.cfctechnology.travel.repository.TourCategoryRepository;
import com.cfctechnology.travel.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Page<Tour> users;
        if (name != null && !name.isEmpty()) {
            users = tourRepository.findByNameContaining(name, pageable);
        } else {
            users = tourRepository.findAll(pageable);
        }
        return new PageResult<>(users.getContent(), users.getTotalPages());
    }

    public Tour createTour(Tour tour, long destinationId) {
        Optional<Destination> destination = destinationRepository.findById(destinationId);
        if(destination.isPresent()) {
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
                bookingRepository.deleteById(booking.getBookingId());
            }
            List<TourCategory> tourCategories = tour.get().getTourCategories();
            for(TourCategory tourCategory : tourCategories) {
                tourCategoryRepository.deleteById(tourCategory.getId());
            }
            tourRepository.deleteById(id);
        }
        tourRepository.deleteById(id);
    }
}