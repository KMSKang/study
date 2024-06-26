package kr.tastyhouse.www.controller;

import kr.tastyhouse.www.auth.PrincipalDetails;
import kr.tastyhouse.www.model.User;
import kr.tastyhouse.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/test/login")
  public @ResponseBody String testLogin(Authentication authentication,
                                        @AuthenticationPrincipal PrincipalDetails userDetails) { // DI(의존성 주입)
    System.out.println("/test/login =====");
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    System.out.println("authentication: " + principalDetails.getUser());

    System.out.println("userDetails: " + userDetails.getUser());
    return "세션 정보 확인하기";
  }

  @GetMapping("/test/oauth/login")
  public @ResponseBody String testOauthLogin(Authentication authentication,
                                             @AuthenticationPrincipal OAuth2User oauth) { // DI(의존성 주입)
    System.out.println("/test/login =====");
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
    System.out.println("authentication: " + oAuth2User.getAttributes());

    System.out.println("oauth2User: " + oauth.getAttributes());
    return "세션 정보 확인하기";
  }

//  @GetMapping("/test/login")
//  public @ResponseBody String testLogin1(Authentication authentication,
//                                        @AuthenticationPrincipal UserDetails userDetails) { // DI(의존성 주입)
//    System.out.println("/test/login =====");
//    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//    System.out.println("authentication: " + principalDetails.getUser());
//
//    System.out.println("userDetails: " + userDetails.getUsername());
//    return "세션 정보 확인하기";
//  }

  @GetMapping({"", "/"})
  public String index() {
    return "index";
  }

  @GetMapping("/user")
  public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    System.out.println("principalDetails: " + principalDetails.getUser());
    return "user";
  }

  @GetMapping("/admin")
  public @ResponseBody String admin() {
    return "admin";
  }

  @GetMapping("/manager")
  public @ResponseBody String manager() {
    return "manager";
  }

  @GetMapping("/loginForm")
  public String loginForm() {
    return "loginForm";
  }

  @GetMapping("/joinForm")
  public String joinForm() {
    return "joinForm";
  }

  @PostMapping("/join")
  public String join(User user) {
    System.out.println(user);
    user.setRole("ROLE_USER");
    String rawPassword = user.getPassword();
    String encPassword = bCryptPasswordEncoder.encode(rawPassword);
    user.setPassword(encPassword);
    userRepository.save(user);
    return "redirect:/loginForm";
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("/info")
  public @ResponseBody String info() {
    return "개인정보";
  }

  // @PostAuthorize() // 컨트롤러 접근 후
  @PreAuthorize("hasRole('ROLE_MANAGER') OR hasRole('ROLE_ADMIN')") // 컨트롤러 접근 전
  @GetMapping("/data")
  public @ResponseBody String data() {
    return "데이터정보";
  }

}
