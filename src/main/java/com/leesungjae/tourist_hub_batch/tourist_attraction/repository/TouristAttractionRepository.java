package com.leesungjae.tourist_hub_batch.tourist_attraction.repository;

import com.leesungjae.tourist_hub_batch.entity.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Long> {
}
