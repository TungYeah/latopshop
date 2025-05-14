package vn.minhtung.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.lang.NonNull;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

  @Bean
  public ViewResolver viewResolver() {
    final InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setViewClass(JstlView.class);
    bean.setPrefix("/WEB-INF/view/");
    bean.setSuffix(".jsp");
    return bean;
  }

  @Override
  public void configureViewResolvers(@NonNull ViewResolverRegistry registry) {
    registry.viewResolver(viewResolver());
  }

  @Override
  public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
    registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
    registry.addResourceHandler("/client/**").addResourceLocations("/resources/client/");
  }

  // tắt sercurity tạm thời
  // @Configuration
  // public class SecurityConfig {
  // @Bean
  // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
  // Exception {
  // http
  // .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
  // .csrf(csrf -> csrf.disable()); // Tắt CSRF nếu cần
  // return http.build();
  // }
  // }

}
