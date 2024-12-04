package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.Destination;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.Review;
import com.cfctechnology.travel.model.User;
import com.cfctechnology.travel.model.dto.ReviewDTO;
import com.cfctechnology.travel.repository.DestinationRepository;
import com.cfctechnology.travel.repository.ReviewRepository;
import com.cfctechnology.travel.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private  ReviewRepository reviewRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  DestinationRepository destinationRepository;
    @Autowired
    private ModelMapper modelMapper;


    public Review getReview(long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    public List<Review> getReviews(long id) {
        return reviewRepository.findByDestinationDestinationId(id);
    }

    public PageResult<Review> getPageReviews(int page, int size, LocalDate date){

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews;
        if (date != null) {
            reviews = reviewRepository.findByReviewDateContaining(date, pageable);
        } else {
            reviews = reviewRepository.findAll(pageable);
        }
        return new PageResult<>(reviews.getContent(), reviews.getTotalPages());
    }

    public Review createReview(ReviewDTO reviewDTO  , long destinationId ) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));

        Optional<Destination> destination = destinationRepository.findById(destinationId);

        Review review =  modelMapper.map(reviewDTO, Review.class);

        review.setUser(user);
        review.setDestination(destination.get());

        return reviewRepository.save(review);

    }

    public Review updateReview(Review review, long destinationId, long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));

        Optional<Destination> destination = destinationRepository.findById(destinationId);

        Optional<Review> reviewOptional = reviewRepository.findById(id);

        if (reviewOptional.isPresent()) {
            reviewOptional.get().setReviewDate(LocalDate.now());
            reviewOptional.get().setRating(review.getRating());
            reviewOptional.get().setComment(review.getComment());
            reviewOptional.get().setUser(user);
            reviewOptional.get().setDestination(destination.isPresent() ?destination.get() :null);

            return reviewRepository.save(reviewOptional.get());
        }
        return null;
    }

    public void deleteReview() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));
        reviewRepository.deleteById(user.getUserId());
    }



}
