package com.cfctechnology.travel.controller.admin;

import com.cfctechnology.travel.model.Booking;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController("AdminBooking")
@RequestMapping("admin/booking")
public class BookingController {
    @Autowired
    private  BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBooking(@PathVariable long id) {
        if(bookingService.getBookingById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail 404", "not found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get successfully", bookingService.getBookingById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllReviews(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                        @RequestParam(value = "date", required = false) String dateString) {
        LocalDate date = null;
        if (dateString != null && !dateString.isEmpty()) {
            try {
                date = LocalDate.parse(dateString);
            } catch (DateTimeParseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("error", "Invalid date format. Expected format: yyyy-MM-dd", null));
            }
        }

        PageResult<Booking> bookings = bookingService.getPageBookings(page, size, date);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Get all successfully", bookings));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable("id") long id) {
        bookingService.deleteBookingById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully",null ));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseObject> deleteAllBooking() {
        bookingService.deleteALl();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully",null ));
    }
}
