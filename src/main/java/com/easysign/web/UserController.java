package com.easysign.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.easysign.dao.AppUserRepository;
import com.easysign.entities.AppUser;
import com.easysign.service.AccountService;
import com.easysign.service.DataRegister;
import com.easysign.service.GaiaService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RefreshScope
public class UserController {

	///////////////////////////////////////////////// :
	////// USE DTO for mapping Bean with formated data/////
	//////////////////////////////////////////////////
	@Autowired
	private AccountService accountService;

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private GaiaService gaiaService;

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${app.message}")
	private String welcomeMessage;

	@GetMapping(value = "/refresh-config")
	public @ResponseBody String refreshConfig() {

		log.info("refreshing configuration lunched");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity request = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate
				.postForEntity("http://localhost:8888/actuator/bus-refresh/", request, String.class);

		return responseEntity.toString();
	}

	@GetMapping("/users")
	public List<AppUser> list() {
		log.info("STARTING WITH : {} ", welcomeMessage);
		log.info("get users list");
		return appUserRepository.findAll();
	}

	@GetMapping("/user/{mail}")
	public AppUser getUserByMail(@PathVariable String mail) {
		log.info("get user with mail : {}", mail);
		return appUserRepository.findByEmail(mail);
	}

	@GetMapping("/remove-user/{mail}")
	public @ResponseBody void removeUser(@PathVariable String mail) {
		log.info("remove user with mail : {} ", mail);
		AppUser userToDelete = appUserRepository.findByEmail(mail);
		appUserRepository.delete(userToDelete);

	}

	@PostMapping("/register")
	public void register(@RequestBody DataRegister userForm) {
		log.info("register user with params : {} ", userForm);
		AppUser result = accountService.saveUser(userForm);
		return;
	}

	@PostMapping("/update-user/{mail}")
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(@PathVariable String mail, @RequestBody DataRegister userForm) {
		log.info("update user [{}] with params : {}", mail, userForm);
		AppUser result = accountService.updateUser(mail, userForm);
		return;
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public AppUser authenticate(@RequestBody AppUser userDto) {
		AppUser user = appUserRepository.findByEmail(userDto.getEmail());
		if (user != null) {
			return user;
		}
		return null;
	}

	@PostMapping("/sign")
	@ResponseStatus(HttpStatus.OK)
	public String loadData(@RequestBody Map<String, String> dataObject) {
		String userMail = dataObject.get("mail");
		AppUser user = accountService.loadUserByUsername(userMail);
		// add user details
		dataObject.put("firstName", user.getFirstname());
		dataObject.put("lastName", user.getLastname());

		log.info("send informations to (gaia service) : {} ", dataObject);

		return gaiaService.signNotif(dataObject);
	}

	@PostMapping("/send-to-blockchain-service")
	@ResponseStatus(HttpStatus.OK)
	public String loadDataToBlockchain(@RequestBody Map<String, Object> dataObject) {

		String userMail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		dataObject.put("SenderMail", userMail);
		log.info("send informations to (blockchain service) : {} ", dataObject);

		Map<String, String> obj = new HashMap<>();
		obj.put("senderMail", userMail);
		obj.put("docBase64", (String) dataObject.get("pdfB64"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = "http://localhost:9999/add-document/";
		HttpEntity request = new HttpEntity<>(obj, headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
		String docNum = responseEntity.getBody();
		log.info("add document base64 to blockchain : {} ---- result : {} ", dataObject.get("pdfB64"),
				responseEntity.toString());

		url = "http://localhost:9999/add-signers-toDoc/" + docNum;
		request = new HttpEntity<>(dataObject.get("mails"), headers);
		responseEntity = restTemplate.postForEntity(url, request, String.class);
		log.info("add signers : {} to document [{}]  ---- result : {} ", dataObject.get("mails"), docNum,
				responseEntity.toString());

		return responseEntity.getBody();
	}

	@PostMapping("/signing-process/{transactionId}")
	@ResponseStatus(HttpStatus.OK)
	public Map signing(@PathVariable String transactionId, @RequestBody Map<String, String> dataObject) {
		log.info("otpk signing process from gaia service");
		return gaiaService.signingProcess(transactionId, dataObject);

	}

	@GetMapping("/get-tasks")
	public Map getTasks() {
		log.info("get tasks attributed to authenticated user from gaia service");
		String userMail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return gaiaService.getTasksByUser(userMail);

	}

	@GetMapping("/get-tasks/{mail}")
	public Map getTasks(@PathVariable String mail) {
		log.info("get tasks attributed to user with mail : {}", mail);
		return gaiaService.getTasksByUser(mail);
	}

	@GetMapping("/get-task-description/{transactionId}/{taskName}")
	public Map getTask(@PathVariable String transactionId, @PathVariable String taskName) {
		log.info("get task description with transactionId : [{}] and taskName : {}", transactionId, taskName);
		return gaiaService.getTaskForm(transactionId, taskName);
	}

}
