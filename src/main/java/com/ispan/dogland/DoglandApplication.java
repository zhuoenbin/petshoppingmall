package com.ispan.dogland;

import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dao.tweet.TweetFollowListRepository;
import com.ispan.dogland.model.dao.tweet.TweetLikeRepository;
import com.ispan.dogland.model.dao.tweet.TweetNotificationRepository;
import com.ispan.dogland.model.dao.tweet.TweetRepository;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import com.ispan.dogland.model.entity.tweet.*;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@SpringBootApplication
public class DoglandApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoglandApplication.class, args);
	}


	//測試方法
	@Bean
	@Transactional
	public CommandLineRunner commandLineRunner(UserRepository userRepository,
											   DogRepository dogRepository,
											   EmployeeRepository employeeRepository,
											   TweetRepository tweetRepository,
											   ProductRepository productRepository,
											   TweetService tweetService,
											   TweetLikeRepository tweetLikeRepository,
											   TweetFollowListRepository tweetFollowListRepository,
											   TweetNotificationRepository tweetNotificationRepository) {
		return runner -> {

			//測試方法
			//使用者通知，monted使用的方法
//			Users u1 = userRepository.findUserAndTweetNotificationByUserId(2);
//			List<TweetNotification> li = u1.getTweetNotifications();


//			Users u1 = userRepository.findUserAndTweetNotificationByUserId(2);
//			TweetNotification tweetNotification = new TweetNotification();
//			tweetNotification.setPostTime(new Date()); // 设置 postTime
//			tweetNotification.setContent("Notification content"); // 设置 content
//			tweetNotification.setIsRead(0); // 设置 isRead
//			u1.addTweetNotification(tweetNotification);
//			userRepository.save(u1);


			//新增功能
//			Tweet b = tweetRepository.findTweetAndDogsByTweetIdByLEFTJOIN(50);
//			Dog c = dogRepository.findByDogId(22);
//			b.addDog(c);
//			Tweet b2 = tweetRepository.save(b);

			//移除功能
//			Tweet b = tweetRepository.findTweetAndDogsByTweetId(50);
//			if(b != null){
//				List<Dog> dogs = b.getDogs();
//				for(Dog d : dogs){
//					if(d.getDogId()==24){
//						dogs.remove(d);
//						tweetRepository.save(b);
//					}
//				}
//			}else{
//				System.out.println("no");
//			}



		};

	}

	private void findUserAndDogsByUserId(UserRepository userRepository,DogRepository dogRepository){
//		Users u1 = userRepository.findUserAndDogsByUserId(1);
//		List<Dog> dogs = u1.getDogs();
//		System.out.println(dogs);
	}
	private void createDogAndAddUser(UserRepository userRepository,DogRepository dogRepository){
		Users u1 = userRepository.findByUserId(1);

		Dog dog1 = new Dog();
		dog1.setDogId(1);
		dog1.setDogName("Buddy");
		dog1.setDogImgPathLocal("/path/to/local/image1.jpg");
		dog1.setDogImgPathCloud("https://cloud.example.com/path/to/image1.jpg");
		dog1.setDogImgPublicId("image1_public_id");
		dog1.setDogGender("Male");
		dog1.setDogIntroduce("Friendly and playful dog");
		dog1.setDogBirthDate(new Date()); // Set birth date to current date
		dog1.setDogWeight(5.0);
		dog1.setDogBreed("Labrador Retriever");

		Dog dog2 = new Dog();
		dog2.setDogId(2);
		dog2.setDogName("Luna");
		dog2.setDogImgPathLocal("/path/to/local/image2.jpg");
		dog2.setDogImgPathCloud("https://cloud.example.com/path/to/image2.jpg");
		dog2.setDogImgPublicId("image2_public_id");
		dog2.setDogGender("Female");
		dog2.setDogIntroduce("Calm and affectionate dog");
		dog2.setDogBirthDate(new Date()); // Set birth date to current date
		dog2.setDogWeight(3.5); // Assuming 2 represents small size
		dog2.setDogBreed("Golden Retriever");

		dog1.setUser(u1);
		dog2.setUser(u1);

		dogRepository.save(dog1);
		dogRepository.save(dog2);
	}


}


