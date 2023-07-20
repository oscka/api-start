package com.osckorea.openmsa.starter.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.osckorea.openmsa.global.annotation.CustomLog;
import com.osckorea.openmsa.global.exception.Exception400;
import com.osckorea.openmsa.global.exception.Exception404;
import com.osckorea.openmsa.global.redis.RedisOperator;
import com.osckorea.openmsa.global.util.damo.DamoScpHandler;
import com.osckorea.openmsa.starter.user.domain.User;
import com.osckorea.openmsa.starter.user.dto.UserDto;
import com.osckorea.openmsa.starter.user.feign.AuthFeignClient;
import com.osckorea.openmsa.starter.user.kafka.UserKafkaProducer;
import com.osckorea.openmsa.starter.user.kafka.event.UserFindEvent;
import com.osckorea.openmsa.starter.user.repository.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

	private final RedisOperator<User> redisOperator;
	
	private final UserRepository userRepository;

	private final AuthFeignClient authFeignClient;
	
	private final UserKafkaProducer userKafkaProducer;

	private final DamoScpHandler damoScpHandler;

	/**
	 * 회원가입
	 * @param save
	 */
	@CustomLog
	public void register(UserDto.SaveRequest save){
		Optional<User> user = userRepository.findByLoginId(save.getLoginId());
		if (user.isPresent()){
			throw new Exception400("loginId", "중복되는 아이디입니다.");
		}
		userRepository.insert(save.toEntity());
	}

	/**
	 * 중복 아이디 Check
	 * @param loginId
	 * @return
	 */
	public boolean existsByLoginId(String loginId){
		return userRepository.existsByLoginId(loginId);
	}

	/**
	 * 회원정보 by loginID
	 * @param loginId
	 * @return
	 */
	@CustomLog
	public UserDto.UserInfoResponse findByLoginId(String loginId){

		String redisKey = "findByLoginIn."+loginId;

		User user = redisOperator.getValue(redisKey);

		if(user == null){
			log.info("redis cache 조회 실패");
			user = userRepository.findByLoginId(loginId).orElseThrow(()->new Exception404("존재하지 않는 계정입니다."));
			
			log.info("redis cache 등록 >> " + redisKey);
			redisOperator.set(redisKey, user, 100, TimeUnit.SECONDS);
		}

		// D'Amo 테스트
		log.info("[양방향] (OSCKOREA-POL-1) test ================================================");
		String strEncB64_1 = damoScpHandler.ScpEncB64("OSCKOREA-POL-1",user.getLoginId());
		log.info("[암호화] ScpEncB64_1 : " + strEncB64_1);
		String strDecB64_1_OK = damoScpHandler.ScpDecB64("OSCKOREA-POL-1",strEncB64_1);
		log.info("[복호화] (정상키) strEncB64_1 >> strDecB64_1_OK : " + strDecB64_1_OK);
		String strDecB64_1_FAIL = damoScpHandler.ScpDecB64("OSCKOREA-POL-2",strEncB64_1);
		log.info("[복호화] (비정상키) strEncB64_1 >> strDecB64_1_FAIL : " + strDecB64_1_FAIL);

		// log.info("[양방향] (OSCKOREA-POL-2) test ================================================");
		String strEncB64_2 = damoScpHandler.ScpEncB64("OSCKOREA-POL-2",user.getLoginId());
		log.info("[암호화] (OSCKOREA-POL-2) ScpEncB64_2 : " + strEncB64_2);
		String strDecB64_2_OK = damoScpHandler.ScpDecB64("OSCKOREA-POL-2",strEncB64_2);
		log.info("[복호화] (정상키) strEncB64_2 >> strDecB64_2_OK : " + strDecB64_2_OK);
		String strDecB64_2_FAIL = damoScpHandler.ScpDecB64("OSCKOREA-POL-1",strEncB64_2);
		log.info("[복호화] (비정상키) strEncB64_2 >> strDecB64_2_FAIL : " + strDecB64_2_FAIL);

		// log.info("[단방향] test ================================================");
		String strHashB64 = damoScpHandler.ScpHashB64(user.getLoginId());
		log.info("[암호화] ScpHashB64 String : " + strHashB64);


		// Kafka 테스트
		UserFindEvent userFindEvent = UserFindEvent.builder()
				.loginId(loginId)
				.build();
		userKafkaProducer.findUser(userFindEvent);

		log.info("Login Id 확인 : " + user.getLoginId());
		return user.toDto();
	}

	/**
	 * 회원정보 수정
	 * @param id
	 * @param update
	 */
	@CustomLog
	public void update(UUID id, UserDto.UpdateRequest update){
		User user = userRepository.findById(id).orElseThrow(()->new Exception404("존재하지 않는 계정입니다."));
		userRepository.changeUserInfo(id,update.getName(),update.getEmail(), update.getPhone());
	}


	/**
	 * 회원 탈퇴
	 * @param id
	 */
	@CustomLog
	public void delete(UUID id){
		User account = userRepository.findById(id).orElseThrow(()->new Exception404("존재하지 않는 계정입니다."));
		userRepository.delete(account);
	}

	@CustomLog
	public Map<Object, String> login(UserDto.LoginRequest request){
		return authFeignClient.getToken(request);
	}

}
