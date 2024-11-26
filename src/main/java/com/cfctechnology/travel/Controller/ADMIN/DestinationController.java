package com.cfctechnology.travel.Controller.ADMIN;

import com.cfctechnology.travel.Model.Destination;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Model.User;
import com.cfctechnology.travel.Service.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("AdminDestination")
@RequestMapping("admin/destination")
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

    @PostMapping("")
    public ResponseEntity<ResponseObject> addDestination(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("name") String name,
                                                         @RequestParam("description") String description,
                                                         @RequestParam("location") String location)
                                                          throws Exception {
        Destination des = this.destinationService.createDestination(file, name, description, location);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully",des));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateDestination(@PathVariable("id") long id,
                                                            @RequestParam("file") MultipartFile file,
                                                            @RequestParam("name") String name,
                                                            @RequestParam("description") String description,
                                                            @RequestParam("location") String location) throws Exception {
        Destination des = this.destinationService.updateDestination(file, name, description, location,id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully",des ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteDestination(@PathVariable("id") long id) {
        this.destinationService.deleteDestination(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully",null));
    }
}
