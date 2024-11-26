package com.cfctechnology.travel.Controller.ADMIN;

import com.cfctechnology.travel.Model.Booking;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Model.Review;
import com.cfctechnology.travel.Service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("AdminBooking")
@RequestMapping("admin/booking")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBooking(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get successfully", this.bookingService.getBookingById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllReviews(@RequestParam(value ="page",defaultValue = "0") int page,
                                                        @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                        @RequestParam(value = "date", required = false) LocalDate date){

//        LocalDate bookingDate = LocalDate.parse(date);
        PageResult<Booking> bookings = this.bookingService.getPageBookings(page,size, date);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get  all successfully", bookings));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addBooking(@RequestBody Booking booking,
                                                     @RequestParam(value="userId") long userId,
                                                     @RequestParam(value="tourId") long tourId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully", this.bookingService.createBooking(booking, userId, tourId)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBooking(@PathVariable("id") long id,
                                                        @RequestBody Booking booking,
                                                        @RequestParam(value="userId") long userId,
                                                        @RequestParam(value="tourId") long tourId
                                                        ) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully", this.bookingService.updateBooking(booking , userId, tourId,id)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable("id") long id) {
        this.bookingService.deleteBookingById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully",null ));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<ResponseObject> deleteAllBooking() {
        this.bookingService.deleteALl();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully",null ));
    }
}
