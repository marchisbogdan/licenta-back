package com.onnisoft.wahoo.api.common;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;

public interface Common {
	GenericResponseDTO<String> getTokenFromRemote();

	void setupRestTemplate(final String token);
}
