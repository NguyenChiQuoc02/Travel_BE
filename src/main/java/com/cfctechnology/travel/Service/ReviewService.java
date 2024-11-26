package com.cfctechnology.travel.Service;

import com.cfctechnology.travel.Model.Destination;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.Review;
import com.cfctechnology.travel.Model.User;
import com.cfctechnology.travel.Repository.DestinationRepository;
import com.cfctechnology.travel.Repository.ReviewRepository;
import com.cfctechnology.travel.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReviewService {
    private  final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,DestinationRepository destinationRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.destinationRepository = destinationRepository;
    }

    public Review getReview(long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    public PageResult<Review> getPageReviews(int page, int size, LocalDate date){

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews;
        if (date != null) {
            reviews = this.reviewRepository.findByReviewDateContaining(date, pageable);
        } else {
            reviews = this.reviewRepository.findAll(pageable);
        }
        return new PageResult<>(reviews.getContent(), reviews.getTotalPages());
    }

    public Review createReview(Review review , long userId, long destinationId ) {
        Optional<User> user = this.userRepository.findById(userId);
        Optional<Destination> destination = this.destinationRepository.findById(destinationId);
        Review newReview = new Review();
        newReview.setReviewDate(LocalDate.now());
        newReview.setRating(review.getRating());
        newReview.setComment(review.getComment());
        newReview.setUser(user.get());
        newReview.setDestination(destination.get());
        return this.reviewRepository.save(newReview);

    }

    public Review updateReview(Review review,long userId, long destinationId, long id) {
        Optional<User> user = this.userRepository.findById(userId);
        Optional<Destination> destination = this.destinationRepository.findById(destinationId);
        Optional<Review> reviewOptional = this.reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            reviewOptional.get().setReviewDate(LocalDate.now());
            reviewOptional.get().setRating(review.getRating());
            reviewOptional.get().setComment(review.getComment());
            reviewOptional.get().setUser(user.isPresent()? user.get(): null);
            reviewOptional.get().setDestination(destination.isPresent() ?destination.get() :null);
            return this.reviewRepository.save(reviewOptional.get());
        }
        return null;
    }

    public void deleteReview(long id) {
        this.reviewRepository.deleteById(id);
    }



}
