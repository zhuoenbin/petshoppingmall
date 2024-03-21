package com.ispan.dogland.model.entity.activity;

import jakarta.persistence.*;

@Entity
@Table
public class ActivityGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer galleryId;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private VenueActivity venueActivity;
    private String galleryImgUrl;
    private String galleryPublicId;
    private String galleryImgData;
}
