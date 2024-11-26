package com.cfctechnology.travel.Controller.HOME;

import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Model.Review;
import com.cfctechnology.travel.Service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController("HomeReviewController")
@RequestMapping("/home/review")
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




}
