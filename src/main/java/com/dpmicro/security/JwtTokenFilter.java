package com.dpmicro.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(httpServletRequest);

		try {
			if (StringUtils.isNoneBlank(token)) {
				String validatedToken = jwtTokenProvider.validateToken(token);

				httpServletRequest.getHeader("Authorization");

				String header = String.valueOf(validatedToken);
				Authentication auth = jwtTokenProvider.getAuthentication(String.valueOf(validatedToken));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		catch (Exception ex) {
			httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), ex);
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
