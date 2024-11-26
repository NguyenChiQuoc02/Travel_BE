package com.cfctechnology.travel.Controller.HOME;

import com.cfctechnology.travel.Model.Destination;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Service.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("HomeDestination")
@RequestMapping("/home/destination")
public class DestinationController {

    private final DestinationService destinationService;
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getDestination(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get  successfully", this.destinationService.getDestinationById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllDestination(@RequestParam(value ="page",defaultValue = "0") int page,
                                                            @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                            @RequestParam(value = "name", required = false) String name) {
        PageResult<Destination> des = this.destinationService.getPageDestination(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get all successfully", des));
    }
}
