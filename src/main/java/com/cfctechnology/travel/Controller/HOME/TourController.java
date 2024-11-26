package com.cfctechnology.travel.Controller.HOME;

import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Model.Tour;
import com.cfctechnology.travel.Service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("HomeTour")
@RequestMapping("/home/tour")
public class TourController {

    private final TourService tourService;
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTourById(@PathVariable("id") long id) {
        return ResponseEntity.status( HttpStatus.OK).body(new ResponseObject("ok", "get sucessfully",this.tourService.getTourById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getTours(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                   @RequestParam(value = "name", required = false) String name) {
        PageResult<Tour> tours = this.tourService.getPageTours(page , size , name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get sucessfully",tours));
    }
}
