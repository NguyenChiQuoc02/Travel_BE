package com.cfctechnology.travel.controller.home;

import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.model.Tour;
import com.cfctechnology.travel.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin("*")
@RestController("HomeTour")
@RequestMapping("/home/tour")
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

    @GetMapping("/search")
    public ResponseEntity<PageResult<Tour>> searchTours(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "6") int size,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
             ) {
        PageResult<Tour> tours = tourService.searchTours(page,size,name, minPrice, maxPrice, startDate);
        return ResponseEntity.ok(tours);
    }

}
