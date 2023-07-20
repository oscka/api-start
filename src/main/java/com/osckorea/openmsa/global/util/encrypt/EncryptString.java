package com.osckorea.openmsa.global.util.encrypt;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class EncryptString {
	@NotNull
	String value;
}
