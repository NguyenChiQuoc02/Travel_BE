package com.cfctechnology.travel.controller.admin;

import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.model.Tour;
import com.cfctechnology.travel.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/tour")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class TourController {

    @Autowired
    private  TourService tourService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTourById(@PathVariable("id") long id) {
        return ResponseEntity.status( HttpStatus.OK).body(new ResponseObject("ok", "get sucessfully",tourService.getTourById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getTours(@RequestParam(value = "page" ,defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                                   @RequestParam(value = "name", required = false) String name) {
        PageResult<Tour> tours = tourService.getPageTours(page , size , name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get sucessfully",tours));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addTour(@RequestBody Tour tour,
                                                  @RequestParam(value="destinationId") long destinationId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok","add successfully", tourService.createTour(tour, destinationId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTour(@PathVariable("id") long id,
                                                     @RequestParam(value="destinationId") long destinationId,
                                                     @RequestBody Tour tour) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfully", tourService.updateTour(tour,destinationId, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTour(@PathVariable("id") long id) {
        tourService.deleteTour(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","delete successfully",null));

    }
}
