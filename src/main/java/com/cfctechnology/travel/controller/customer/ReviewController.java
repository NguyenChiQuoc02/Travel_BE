package com.cfctechnology.travel.controller.customer;

import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.model.Review;
import com.cfctechnology.travel.model.dto.ReviewDTO;
import com.cfctechnology.travel.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@CrossOrigin("*")
@RestController("CustomerReview")
@RequestMapping("/customer/review")
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class ReviewController {

    @Autowired
    private  ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getReviewById(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get successfully", reviewService.getReview(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllReviews(@RequestParam(value ="page",defaultValue = "0") int page,
                                                        @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                        @RequestParam(value = "date", required = false) LocalDate date){
        PageResult<Review> reviews = reviewService.getPageReviews(page,size, date);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get  all successfully", reviews));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addReview(@RequestBody ReviewDTO reviewDTO
                                                 ){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully", reviewService.createReview(reviewDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateReview(@PathVariable("id") long id,
                                                       @RequestBody Review review,
                                                       @RequestParam("destinationId" ) long destinationId){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfully", reviewService.updateReview(review, destinationId, id)));
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseObject> deleteReview(){
        reviewService.deleteReview();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfully", null));
    }
}
