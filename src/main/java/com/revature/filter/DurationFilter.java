package com.revature.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class DurationFilter extends OncePerRequestFilter{

	private static final Logger log = LoggerFactory.getLogger(DurationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		
		try {
			MDC.put("requestId", UUID.randomUUID());
			
			//Continue on to the next filter
			filterChain.doFilter(request, response);
			
		}finally {
			long endTime = System.currentTimeMillis();
			String duration = String.format("%d", endTime - startTime);
			
			MDC.put("duration", String.format("%s ms", duration));
			
			log.info("Request has finished processing and took {} ms", duration);
			MDC.clear();
		}
	}

}
