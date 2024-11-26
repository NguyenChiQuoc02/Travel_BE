package com.cfctechnology.travel.Service;

import com.cfctechnology.travel.Model.Destination;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Repository.DestinationRepository;
import com.cfctechnology.travel.Utils.FileUploadImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

@Service
public class DestinationService {
    private final DestinationRepository destinationRepository;
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Destination getDestinationById(long id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        return destination.orElse(null);
    }

    public PageResult<Destination> getPageDestination(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<Destination> des;
        if (name != null && !name.isEmpty()) {
            des = this.destinationRepository.findByNameContaining(name, pageable);
        } else {
            des = this.destinationRepository.findAll(pageable);
        }
        return new PageResult<>(des.getContent(), des.getTotalPages());
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
            return this.destinationRepository.save(newDes);
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
        Optional<Destination> destinationOptional = this.destinationRepository.findById(id);
        if (destinationOptional.isPresent()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String fileCode = FileUploadImage.saveFile(fileName,multipartFile);
            String fileUrl = fileCode + "-" + fileName;
            Destination updateDes = destinationOptional.get();
            updateDes.setName(name);
            updateDes.setDescription(description);
            updateDes.setLocation(location);
            updateDes.setImageUrl(fileUrl);
            return this.destinationRepository.save(updateDes);

        }
        return null;
    }

    public void deleteDestination(long id) {

        this.destinationRepository.deleteById(id);
    }
}
