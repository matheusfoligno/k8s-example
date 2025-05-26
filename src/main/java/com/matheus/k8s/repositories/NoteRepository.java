package com.matheus.k8s.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.matheus.k8s.entities.NoteEntity;

@Repository
public interface NoteRepository extends MongoRepository<NoteEntity, String> {

}
