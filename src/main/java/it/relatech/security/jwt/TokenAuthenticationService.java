package it.relatech.security.jwt;

import static java.util.Collections.emptyList;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {
	
	static final long EXPIRATIONTIME = 1_800_000; // 30 minutes
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	private static Logger log = LoggerFactory.getLogger(TokenAuthenticationService.class);
	
	static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	static Authentication getAuthentication(HttpServletRequest request) {
	    String token = request.getHeader(HEADER_STRING);
	    log.info(token);
	    if (token != null) {
	      // parse the token.
	      
	    	try {
	    		String user = Jwts.parser()
    				.setSigningKey(SECRET)
			          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
			          .getBody()
			          .getSubject();
	    		log.info("USER: "+user);
	    		
	    		return user != null ?
	    		          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
	    		          null;
	    	} catch (Exception e) {
	    		log.info("Exception: "+e);
	    		return null;
	    	} 
	    }
	    return null;
	  }
}
