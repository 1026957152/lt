package com.lt.dom;

import java.util.Date;

import com.google.gson.Gson;
import com.lt.dom.error.TokenRefreshException;
import com.lt.dom.vo.LogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
//import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;
	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;

/*	public static JwtUtils(String jwtSecret, int jwtExpirationMs) {
		this.jwtSecret = jwtSecret;
		this.jwtExpirationMs = jwtExpirationMs;

	}*/

	public String generateJwtToken(int i, Authentication authentication) {
		User userPrincipal = (User) authentication.getPrincipal();
		Gson gson = new Gson();


		return Jwts.builder()
				.setSubject(gson.toJson(new LogVo(i,userPrincipal.getUsername())))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String generateJwtToken(String name) {



		return Jwts.builder()
				.setSubject(name)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	public String generateJwtToken(int i, String name) {
		Gson gson = new Gson();


		return Jwts.builder()
				.setSubject(gson.toJson(new LogVo(i,name)))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {

			logger.error("JWT token is expired: {}", e.getMessage());
			throw new TokenRefreshException("JWT token is expired: {}"+ e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	public static void main(String[] args) {
		int jwtExpirationMs = 86400000;
		String jwtSecret = "bezKoderSecretKey";
		JwtUtils jwtUtils = null;//new JwtUtils(jwtSecret,jwtExpirationMs);

		String jwt = jwtUtils.generateJwtToken("13468801684");
		String phone = jwtUtils.getUserNameFromJwtToken(jwt);

		System.out.println(phone);
	}
}