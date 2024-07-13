package com.example.residentsupportservices.Repository;

import com.example.residentsupportservices.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
