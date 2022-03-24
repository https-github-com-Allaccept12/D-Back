package TeamDPlus.code.jwt;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String)request.getAttribute("exception");
        JwtErrorCode errorCode;

        if (exception == null) {
            errorCode = JwtErrorCode.UNAUTHORIZEDException;
            setResponse(response, errorCode);
            return;
        }

        if (exception.equals("ExpiredJwtException")) {
            errorCode = JwtErrorCode.ExpiredJwtException;
            setResponse(response, errorCode);
            return;
        }

    }
    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, JwtErrorCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());

        response.getWriter().print(json);
    }

}
