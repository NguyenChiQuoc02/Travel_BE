package com.cfctechnology.travel.Controller.CUSTOMER;

import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Model.Review;
import com.cfctechnology.travel.Service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("CustomerReview")
@RequestMapping("/customer/review")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getReviewById(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get successfully", this.reviewService.getReview(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllReviews(@RequestParam(value ="page",defaultValue = "0") int page,
                                                        @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                        @RequestParam(value = "date", required = false) LocalDate date){
        PageResult<Review> reviews = this.reviewService.getPageReviews(page,size, date);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get  all successfully", reviews));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addReview(@RequestBody Review review,
                                                    @RequestParam("userId" ) long userId,
                                                    @RequestParam("destinationId" ) long destinationId){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully", this.reviewService.createReview(review, userId, destinationId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateReview(@PathVariable("id") long id,
                                                       @RequestBody Review review,
                                                       @RequestParam("userId" ) long userId,
                                                       @RequestParam("destinationId" ) long destinationId){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfully", this.reviewService.updateReview(review,userId, destinationId, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteReview(@PathVariable("id") long id){
        this.reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfully", null));
    }
}
