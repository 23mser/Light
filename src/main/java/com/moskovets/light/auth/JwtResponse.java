package com.moskovets.light.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class JwtResponse {
    @Schema(description = "Токен доступа", example = "fdf8g76gf68d7gfd8g6dfg78fdg8")
    private String token;
}
