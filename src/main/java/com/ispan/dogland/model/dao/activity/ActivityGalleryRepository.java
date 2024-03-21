package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.ActivityGallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityGalleryRepository extends JpaRepository<ActivityGallery,Integer> {
    ActivityGallery findByGalleryId(Integer galleryId);
}
