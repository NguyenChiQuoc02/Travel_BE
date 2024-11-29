package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.Destination;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.Review;
import com.cfctechnology.travel.model.User;
import com.cfctechnology.travel.repository.DestinationRepository;
import com.cfctechnology.travel.repository.ReviewRepository;
import com.cfctechnology.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private  ReviewRepository reviewRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  DestinationRepository destinationRepository;


    public Review getReview(long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
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

    public Review createReview(Review review  , long destinationId ) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));

        Optional<Destination> destination = destinationRepository.findById(destinationId);

        Review newReview = new Review();
        newReview.setReviewDate(LocalDate.now());
        newReview.setRating(review.getRating());
        newReview.setComment(review.getComment());
        newReview.setUser(user);
        newReview.setDestination(destination.get());

        return reviewRepository.save(newReview);

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
