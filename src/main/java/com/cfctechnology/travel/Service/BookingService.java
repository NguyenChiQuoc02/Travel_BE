package com.cfctechnology.travel.Service;

import com.cfctechnology.travel.Model.*;
import com.cfctechnology.travel.Repository.BookingRepository;
import com.cfctechnology.travel.Repository.TourRepository;
import com.cfctechnology.travel.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository, TourRepository tourRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }

    public Booking getBookingById(long id) {
        Optional<Booking> booking = this.bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public PageResult<Booking> getPageBookings(int page, int size, LocalDate date){

        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookings;
        if (date != null ) {
            bookings = this.bookingRepository.findByBookingDateContaining(date, pageable);
        } else {
            bookings = this.bookingRepository.findAll(pageable);
        }
        return new PageResult<>(bookings.getContent(), bookings.getTotalPages());
    }

    public Booking createBooking(Booking booking, long userId, long tourId) {
        Optional<User> user = this.userRepository.findById(userId);
        Optional<Tour> tour = this.tourRepository.findById(tourId);
        Booking newBooking = new Booking();
        newBooking.setBookingDate(booking.getBookingDate());
        newBooking.setPaymentDate(booking.getPaymentDate());
        newBooking.setAmount(booking.getAmount());
        newBooking.setStatus(booking.getStatus());
        newBooking.setPaymentMethod(booking.getPaymentMethod());
        newBooking.setPaymentStatus(booking.getPaymentStatus());
        newBooking.setUser(user.isPresent() ? user.get() : null);
        newBooking.setTour(tour.isPresent() ? tour.get() : null);
        return this.bookingRepository.save(newBooking);
    }

    public void deleteAllBookingByUserId( long userId) {
        Optional<User> user = this.userRepository.findById(userId);

        if (user.isPresent() ) {
            List<Booking> bookings = user.get().getBookings();
            for (Booking booking : bookings) {
                this.bookingRepository.deleteById(booking.getBookingId());
            }
        }
    }
    public void deleteALl (){
         this.bookingRepository.deleteAll();
    }
    public Double payment(Long userId) {
        return bookingRepository.getTotalAmountByUserId(userId);
    }

    public Booking updateBooking(Booking booking,long userId, long tourId, long id) {
        Optional<Booking> bookingOptional = this.bookingRepository.findById(id);
        Optional<User> user = this.userRepository.findById(userId);
        Optional<Tour> tour = this.tourRepository.findById(tourId);
        if (bookingOptional.isPresent()) {
            Booking upBooking = bookingOptional.get();
            upBooking.setBookingDate(booking.getBookingDate());
            upBooking.setPaymentDate(booking.getPaymentDate());
            upBooking.setAmount(booking.getAmount());
            upBooking.setStatus(booking.getStatus());
            upBooking.setPaymentMethod(booking.getPaymentMethod());
            upBooking.setPaymentStatus(booking.getPaymentStatus());
            upBooking.setUser(user.isPresent() ? user.get() : null);
            upBooking.setTour(tour.isPresent() ? tour.get() : null);
        }
        return null;
    }


    public void deleteBookingById(long id) {
        this.bookingRepository.deleteById(id);
    }
}
