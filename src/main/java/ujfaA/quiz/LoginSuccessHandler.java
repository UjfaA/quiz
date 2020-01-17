package ujfaA.quiz;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ujfaA.quiz.model.User;
import ujfaA.quiz.service.UserService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		super.onAuthenticationSuccess(request, response, authentication);
		
		User user = userService.getUser(authentication.getName());
		user.setLastActive(LocalDateTime.now());
		user = userService.update(user);
		
		HttpSession session = request.getSession(true);
		session.setAttribute("userFirstName", user.getFirstName());
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		
		List<String> roles = getRoles();
		String targetUrl = "";
		
		if(roles.contains("ROLE_ADMIN"))
			targetUrl = "/overview/";
		else if(roles.contains("ROLE_USER"))
			targetUrl = "/quiz";

		return targetUrl;
	}

	private List<String> getRoles() {
		
		ArrayList<String> roles = new ArrayList<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		auth.getAuthorities().forEach(a -> roles.add(a.getAuthority()) );
		return roles;
	}
}
