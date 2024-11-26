package com.cfctechnology.travel.Controller.CUSTOMER;

import com.cfctechnology.travel.Model.Booking;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("CustomerBooking")
@RequestMapping("/customer/booking")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getBooking(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get successfully", this.bookingService.getBookingById(id)));
    }

   @GetMapping("/hi")
   public ResponseEntity<ResponseObject> hi(@RequestParam(value= "userId", defaultValue = "3") long userId) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "payment successfully", this.bookingService.payment(userId)));
   }

//    @GetMapping("")
//    public ResponseEntity<ResponseObject> getAllReviews(@RequestParam(value ="page",defaultValue = "0") int page,
//                                                        @RequestParam(value = "size" ,defaultValue = "10") int size,
//                                                        @RequestParam(value = "date", required = false) LocalDate date){
//        PageResult<Booking> bookings = this.bookingService.getPageBookings(page,size, date);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get  all successfully", bookings));
//    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addBooking(@RequestBody Booking booking,
                                                     @RequestParam(value="userId") long userId,
                                                     @RequestParam(value="tourId") long tourId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully", this.bookingService.createBooking(booking, userId, tourId)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteBooking(@PathVariable("id") long id) {
        this.bookingService.deleteBookingById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully",null ));
    }

    @DeleteMapping("/deleteByUser/{userId}")
    public ResponseEntity<ResponseObject> deleteAllBookingByUserId(@PathVariable("userId") long userId) {
        this.bookingService.deleteAllBookingByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete all successfully",null ));
    }






}
