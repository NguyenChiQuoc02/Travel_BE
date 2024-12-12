package com.cfctechnology.travel.controller.customer;

import com.cfctechnology.travel.model.Booking;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.model.dto.BookingDTO;
import com.cfctechnology.travel.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController("CustomerBooking")
@RequestMapping("/customer/booking")
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class BookingController {

    @Autowired
    private  BookingService bookingService;

    @GetMapping("/current-user")
    public ResponseEntity<ResponseObject> getBooking() {
        if(bookingService.getBookingCurrentUser() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get successfully",bookingService.getBookingCurrentUser() ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail 404", "not found id",null));
    }


    @GetMapping("/total/current-user")
    public ResponseEntity<ResponseObject> getTotalBooking() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get successfully",bookingService.getTotalBookingsCurrentUser() ));
    }

    @GetMapping("/payment")
    public ResponseEntity<ResponseObject> payment(@RequestParam(value= "userId", defaultValue = "3") long userId) {
             return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "payment successfully", bookingService.payment(userId)));
    }



    @PostMapping
    public ResponseEntity<ResponseObject> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        Booking createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "add successfully",createdBooking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateBooking(@RequestBody BookingDTO bookingDTO,@PathVariable long id) {
        Booking updateBooking = bookingService.updateBooking(bookingDTO,id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully",updateBooking));
    }

    @DeleteMapping("/current-user")
    public ResponseEntity<ResponseObject> deleteAllBookingByUserId()  {
        bookingService.deleteByCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete all successfully",null ));
    }


}
