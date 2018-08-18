package com.github.elgleidson.multi.tenant.schema.security;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.github.elgleidson.multi.tenant.schema.domain.Role;
import com.github.elgleidson.multi.tenant.schema.domain.Tenant;
import com.github.elgleidson.multi.tenant.schema.domain.User;
import com.github.elgleidson.multi.tenant.schema.multitenant.TenantContextHolder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
	private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${app.security.authentication.jwt.secret-key}")
	private String jwtSecretKey;
	
	@Value("${app.security.authentication.jwt.expiration-in-seconds:86400}")
	private long jwtExpirationInSeconds;
	
	public String generateToken(Authentication authentication) {
		log.debug("Generating JWT token for authentication {}", authentication);
		Date now = new Date();
		Date validity = new Date(now.getTime() + (jwtExpirationInSeconds * 1000));
		
		User user = (User) authentication.getPrincipal();
		String perfis = authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(","));
		
		return Jwts.builder()
			.setSubject(Long.toString(user.getId()))
			.claim("id", user.getId())
			.claim("username", user.getUsername())
			.claim("email", user.getEmail())
			.claim("roles", perfis)
			.claim("tenant", user.getTenant().getSchema())
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS512, jwtSecretKey)
			.compact();
	}
	
	public Authentication getAuthentication(String authToken) {
		log.debug("Getting authentication for JWT token {}", authToken);
		Claims claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken).getBody();
		User user = new User();
		user.setId(Long.parseLong(claims.getSubject()));
		user.setUsername(claims.get("username").toString());
		user.setEmail(claims.get("email").toString());
		String schema = claims.get("tenant").toString();
		user.setTenant(new Tenant(schema));
		List<Role> perfis = Arrays.stream(claims.get("roles").toString().split(",")).map(Role::new).collect(Collectors.toList());
		TenantContextHolder.setCurrentSchema(schema);
		return new UsernamePasswordAuthenticationToken(user, authToken, perfis);
	}
	
	public boolean validateToken(String authToken) {
//		Jwts.parser().setSigningKey(Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8)));
		try {
			Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature!");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token!");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token!");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token!");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty or null!");
		}
		
		return false;
	}

}
