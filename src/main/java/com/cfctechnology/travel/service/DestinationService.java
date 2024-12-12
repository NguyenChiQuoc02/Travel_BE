package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.Destination;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.repository.DestinationRepository;
import com.cfctechnology.travel.utils.FileUploadImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private  DestinationRepository destinationRepository;

    public Destination getDestinationById(long id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        return destination.orElse(null);
    }

    public PageResult<Destination> getPageDestination(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<Destination> des;
        if (name != null && !name.isEmpty()) {
            des = destinationRepository.findByNameContaining(name, pageable);
        } else {
            des = destinationRepository.findAll(pageable);
        }
        return new PageResult<>(des.getContent(), des.getTotalPages());
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }
    public Destination createDestination( MultipartFile multipartFile, String name, String description, String location) throws IOException {

        try{
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String fileCode = FileUploadImage.saveFile(fileName,multipartFile);
            String fileUrl = fileCode + "-" + fileName;

            Destination newDes = new Destination();

            newDes.setName(name);
            newDes.setDescription(description);
            newDes.setLocation(location);
            newDes.setImageUrl(fileUrl);

            return destinationRepository.save(newDes);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public Destination updateDestination(MultipartFile multipartFile,
                                         String name,
                                         String description,
                                         String location,
                                         long id) throws IOException {
        Optional<Destination> destinationOptional = destinationRepository.findById(id);
        if (destinationOptional.isPresent()) {

            Destination updateDes = destinationOptional.get();
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                String fileCode = FileUploadImage.saveFile(fileName, multipartFile);
                String fileUrl = fileCode + "-" + fileName;
                updateDes.setImageUrl(fileUrl);
            }
            updateDes.setName(name);
            updateDes.setDescription(description);
            updateDes.setLocation(location);


            return destinationRepository.save(updateDes);

        }
        return null;
    }

    public void deleteDestination(long id) {
        destinationRepository.deleteById(id);
    }
}
