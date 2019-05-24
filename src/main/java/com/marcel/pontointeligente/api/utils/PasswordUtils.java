package com.marcel.pontointeligente.api.utils;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

	private static final org.jboss.logging.Logger log = LoggerFactory.logger(PasswordUtils.class);
	
	public static String gerarBCRYPT(String senha) {
		if (senha == null) {
			return senha;
		}
		
		log.info("Gerando hash com o BCypt.");
		BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
		return bcryptEncoder.encode(senha);
	}
}
