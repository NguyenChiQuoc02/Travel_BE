package com.cfctechnology.travel.controller.admin;

import com.cfctechnology.travel.model.Destination;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController("AdminDestination")
@RequestMapping("admin/destination")
//@CrossOrigin("*")
@CrossOrigin(origins = {"https://sound-honestly-bird.ngrok-free.app", "*"})

@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DestinationController {

    @Autowired
    private  DestinationService destinationService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getDestination(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get  successfully", destinationService.getDestinationById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllDestination(@RequestParam(value ="page",defaultValue = "0") int page,
                                                      @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                      @RequestParam(value = "name", required = false) String name) {
        PageResult<Destination> des = destinationService.getPageDestination(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get all successfully", des));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseObject> getDestinationList(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get all successfully", destinationService.getAllDestinations()));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addDestination(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("name") String name,
                                                         @RequestParam("description") String description,
                                                         @RequestParam("location") String location)
                                                          throws Exception {
        Destination des = destinationService.createDestination(file, name, description, location);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully",des));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateDestination(@PathVariable("id") long id,
                                                            @RequestParam(value = "file" , required = false ) MultipartFile file,
                                                            @RequestParam("name") String name,
                                                            @RequestParam("description") String description,
                                                            @RequestParam("location") String location) throws Exception {
        Destination des = destinationService.updateDestination(file, name, description, location,id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully",des ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteDestination(@PathVariable("id") long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully",null));
    }
}
