package com.osckorea.openmsa.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.lang.Nullable;

import com.osckorea.openmsa.global.util.encrypt.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * @author jinseul
 * Spring Data JDBC 설정
 */
@EnableJdbcAuditing
@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

	@Bean
	@Override
	public JdbcCustomConversions jdbcCustomConversions() {
		Encryptor encryptor = new AESEncryptor();
		return new JdbcCustomConversions(List.of(
			new EncryptStringWritingConverter(encryptor),
			new EncryptStringReadingConverter(encryptor),
			new Converter<Clob, String>() {
				@Nullable
				@Override
				public String convert(Clob clob) {
					try {
						return Math.toIntExact(clob.length()) == 0
							? "" : clob.getSubString(1, Math.toIntExact(clob.length()));

					} catch (SQLException e) {
						throw new IllegalStateException("Failed to convert CLOB to String.", e);
					}
				}
			}));
	}

	/**
	 * 엔티티가 저장되기전에 Validation 검사
	 * @param validator
	 * @return
	 */
	@Bean
	@Order
	BeforeSaveCallback<?> validateBeforeSave(Validator validator) {
		return (aggregate, change) -> {
			Set<ConstraintViolation<Object>> violations = validator.validate(aggregate);
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(violations);
			}

			return aggregate;
		};
	}


	@WritingConverter
	static class EncryptStringWritingConverter implements Converter<EncryptString,String> {
		private final Encryptor encryptor;

		public EncryptStringWritingConverter(Encryptor encryptor) {
			this.encryptor = encryptor;
		}
        @Override
        public String convert(EncryptString source) {
            return this.encryptor.encrypt(source.getValue()).getField();
        }
    }

	@ReadingConverter
	static class EncryptStringReadingConverter implements Converter<String, EncryptString> {
		private final Encryptor encryptor;

		public EncryptStringReadingConverter(Encryptor encryptor) {
			this.encryptor = encryptor;
		}

		@Override
		public EncryptString convert(String source) {
            EncryptedField field = new EncryptedField(source);
			String value = this.encryptor.decrypt(field.getCipherTextAndTag(), field.getNonce());
			if (value == null) {
				return null;
			}

			return new EncryptString(value);
		}
	}



	@ReadingConverter
	class StringToBooleanConverter implements Converter<String, Boolean> {

		@Override
		public Boolean convert(String source) {
			return source != null && source.equalsIgnoreCase("Y") ? Boolean.TRUE : Boolean.FALSE;
		}
	}

	@WritingConverter
	class BooleanToStringConverter implements Converter<Boolean, String> {

		@Override
		public String convert(Boolean source) {
			return source != null && source ? "Y" : "N";
		}
	}
}
