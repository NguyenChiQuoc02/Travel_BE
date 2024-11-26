package com.cfctechnology.travel.Service;

import com.cfctechnology.travel.Model.*;
import com.cfctechnology.travel.Repository.BookingRepository;
import com.cfctechnology.travel.Repository.DestinationRepository;
import com.cfctechnology.travel.Repository.TourCategoryRepository;
import com.cfctechnology.travel.Repository.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private final TourRepository tourRepository;
    private final DestinationRepository destinationRepository;
    private final BookingRepository bookingRepository;
    private final TourCategoryRepository tourCategoryRepository;
    public TourService(TourRepository tourRepository,
                       DestinationRepository destinationRepository,
                       BookingRepository bookingRepository,
                       TourCategoryRepository tourCategoryRepository) {
        this.tourRepository = tourRepository;
        this.destinationRepository = destinationRepository;
        this.bookingRepository = bookingRepository;
        this.tourCategoryRepository = tourCategoryRepository;
    }

    public Tour getTourById(long id) {
        Optional<Tour> tour = this.tourRepository.findById(id);
        return tour.orElse(null);
    }

    public PageResult<Tour> getPageTours(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<Tour> users;
        if (name != null && !name.isEmpty()) {
            users = this.tourRepository.findByNameContaining(name, pageable);
        } else {
            users = this.tourRepository.findAll(pageable);
        }
        return new PageResult<>(users.getContent(), users.getTotalPages());
    }

    public Tour createTour(Tour tour, long destinationId) {
        Optional<Destination> destination = this.destinationRepository.findById(destinationId);
        if(destination.isPresent()) {
            Tour newTour = new Tour();
            newTour.setName(tour.getName());
            newTour.setPrice(tour.getPrice());
            newTour.setDestination(destination.get());
            newTour.setStartDate(tour.getStartDate());
            newTour.setEndDate(tour.getEndDate());
            newTour.setDescriptionTour( tour.getDescriptionTour());
            return this.tourRepository.save(newTour);
        }
        return null;
    }
    public Tour updateTour(Tour tour, long destinationId ,long id ) {
        Optional<Tour> tourOptional = this.tourRepository.findById(id);
        Optional<Destination> destination = this.destinationRepository.findById(destinationId);
        if (tourOptional.isPresent()) {
            if(destination.isPresent()) {
                tourOptional.get().setName(tour.getName());
                tourOptional.get().setPrice(tour.getPrice());
                tourOptional.get().setStartDate(tour.getStartDate());
                tourOptional.get().setEndDate(tour.getEndDate());
                tourOptional.get().setDescriptionTour(tour.getDescriptionTour());
                tourOptional.get().setDestination( destination.get());
                return this.tourRepository.save(tourOptional.get());
            }

        }
        return null;
    }

    public void deleteTour(long id) {
       Optional<Tour> tour = this.tourRepository.findById(id);
        if (tour.isPresent() ) {
            List<Booking> bookings = tour.get().getBookings();
            for (Booking booking : bookings) {
                this.bookingRepository.deleteById(booking.getBookingId());
            }
            List<TourCategory> tourCategories = tour.get().getTourCategories();
            for(TourCategory tourCategory : tourCategories) {
                this.tourCategoryRepository.deleteById(tourCategory.getId());
            }
            this.tourRepository.deleteById(id);
        }
        this.tourRepository.deleteById(id);
    }
}
