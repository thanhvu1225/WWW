package com.nguyenvuthanh.mongodb.repository;

import com.nguyenvuthanh.mongodb.model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PositionRepository extends MongoRepository<Position, Integer> {}
