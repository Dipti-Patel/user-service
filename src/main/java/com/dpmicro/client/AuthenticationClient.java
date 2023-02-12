package com.dpmicro.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.dpmicro.service.AuthenticationClientService;

@FeignClient(name = "${com.dpmicro.client.consul.service.name}")
public interface AuthenticationClient extends AuthenticationClientService{

	
	
}
