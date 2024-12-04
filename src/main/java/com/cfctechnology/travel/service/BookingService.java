package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.*;
import com.cfctechnology.travel.model.dto.BookingDTO;
import com.cfctechnology.travel.model.enum_model.EBookingStatus;
import com.cfctechnology.travel.repository.BookingRepository;
import com.cfctechnology.travel.repository.TourRepository;
import com.cfctechnology.travel.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private  BookingRepository bookingRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  TourRepository tourRepository;
    @Autowired
    private ModelMapper modelMapper;


    public Booking getBookingById(long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public List<Booking> getBookingCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));
        List<Booking> bookings = bookingRepository.findBookingsByUserId(user.getUserId());
        return bookings;
    }



    public PageResult<Booking> getPageBookings(int page, int size, LocalDate date){

        Pageable pageable = PageRequest.of(page, size);

        Page<Booking> bookings;
        if (date != null ) {
            bookings = bookingRepository.findByBookingDateEquals(date, pageable);
        } else {
            bookings = bookingRepository.findAll(pageable);
        }

        return new PageResult<>(bookings.getContent(), bookings.getTotalPages());
    }


    public Booking createBooking(BookingDTO bookingDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));

        Tour tour = tourRepository.findById(bookingDTO.getTour().getTourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour không tồn tại."));

        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        booking.setUser(user);
        booking.setTour(tour);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(BookingDTO bookingDTO, long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        Booking updatedBooking = optionalBooking.get();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));
        Tour tour = tourRepository.findById(bookingDTO.getTour().getTourId())
                .orElseThrow(() -> new IllegalArgumentException("Tour không tồn tại."));

        if (!optionalBooking.isPresent()) {
            throw new IllegalArgumentException("Booking với ID " + id + " không tồn tại.");
        }

        updatedBooking.setUser(user);
        updatedBooking.setTour(tour);
        updatedBooking.setBookingDate(bookingDTO.getBookingDate());
        updatedBooking.setStatus(bookingDTO.getStatus());
        updatedBooking.setPaymentDate(updatedBooking.getPaymentDate());
        updatedBooking.setAmount(bookingDTO.getAmount());
        updatedBooking.setPaymentMethod(bookingDTO.getPaymentMethod());
        updatedBooking.setStatus(bookingDTO.getStatus());

//        updatedBooking = modelMapper.map(bookingDTO, Booking.class);
//        updatedBooking.setUser(user);
//        updatedBooking.setTour(tour);
        return bookingRepository.save(updatedBooking);
    }



    public void deleteByCurrentUser() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));

        List<Booking> bookings = user.getBookings();
        if (bookings.isEmpty()) {
            throw new IllegalArgumentException("Người dùng không có booking nào để xóa.");
        }

        for (Booking booking : bookings) {
            booking.setUser(null);
            booking.setStatus(EBookingStatus.DELETED);
            bookingRepository.deleteById(booking.getBookingId());
        }
    }


    public void deleteALl (){
         bookingRepository.deleteAll();
    }

    public Double payment(Long userId) {
        return bookingRepository.getTotalAmountByUserId(userId);
    }

    public void deleteBookingById(long id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking với ID " + id + " không tồn tại.");
        }
        bookingRepository.deleteById(id);
    }
}
