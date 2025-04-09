package com.pet.pet_app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "pets")
public class Pet {
  @Id private String id;
  private String animal;
  private String name;
  private String ownerName;
}
