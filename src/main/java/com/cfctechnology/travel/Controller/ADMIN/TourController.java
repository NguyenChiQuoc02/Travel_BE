package com.cfctechnology.travel.Controller.ADMIN;

import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Model.Tour;
import com.cfctechnology.travel.Service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/tour")
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

    @PostMapping("")
    public ResponseEntity<ResponseObject> addTour(@RequestBody Tour tour,
                                                  @RequestParam(value="destinationId") long destinationId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok","add successfully", this.tourService.createTour(tour, destinationId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTour(@PathVariable("id") long id,
                                                     @RequestParam(value="destinationId") long destinationId,
                                                     @RequestBody Tour tour) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfully", this.tourService.updateTour(tour,destinationId, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTour(@PathVariable("id") long id) {
        this.tourService.deleteTour(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","delete successfully",null));

    }
}
