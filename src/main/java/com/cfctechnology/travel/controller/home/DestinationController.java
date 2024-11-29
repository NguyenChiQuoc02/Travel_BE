package com.cfctechnology.travel.controller.home;

import com.cfctechnology.travel.model.Destination;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("HomeDestination")
@RequestMapping("/home/destination")
public class DestinationController {
    @Autowired
    private  DestinationService destinationService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getDestination(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get  successfully",destinationService.getDestinationById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllDestination(@RequestParam(value ="page",defaultValue = "0") int page,
                                                            @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                            @RequestParam(value = "name", required = false) String name) {
        PageResult<Destination> des = destinationService.getPageDestination(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get all successfully", des));
    }
}
