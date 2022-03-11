package com.example.bogloginms.service;

import com.example.bogloginms.feign.UserServiceFeign;
import com.example.bogloginms.feign.feignDto.OAuthUserDTO;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {

    private UserServiceFeign userServiceFeign;

    public CustomOidcUserService(UserServiceFeign userServiceFeign) {
        this.userServiceFeign = userServiceFeign;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oidcUser.getAttributes();
        OAuthUserDTO userInfo = new OAuthUserDTO();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setName((String) attributes.get("name"));
        userServiceFeign.saveOrUpdateOAuthUser(userInfo);
        return oidcUser;
    }

}
