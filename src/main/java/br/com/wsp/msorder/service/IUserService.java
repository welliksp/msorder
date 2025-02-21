package br.com.wsp.msorder.service;

import br.com.wsp.msorder.dto.UserRequest;
import br.com.wsp.msorder.dto.UserResponse;

public interface IUserService {


    UserResponse save(UserRequest userRequest);
}
