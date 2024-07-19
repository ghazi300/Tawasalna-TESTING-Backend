package com.tawasalna.business.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUpdateDTO {
  private String title;
  private String description;
  private String categoryId;
  private int deliveryTimeInHours;
}
