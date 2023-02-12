package com.example.admin.controller;

import com.example.admin.common.api.CommonResult;
import com.example.admin.jwt.AuthenticationRegisterRequest;
import com.example.admin.jwt.AuthenticationRequest;
import com.example.admin.jwt.AuthenticationResponse;
import com.example.admin.mapper.PageToPageDTOMapper;
import com.example.admin.model.PageDTO;
import com.example.admin.model.ERole;
import com.example.admin.model.User;
import com.example.admin.model.UserDTO;
import com.example.admin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService service;

  @Autowired
  private PageToPageDTOMapper<UserDTO> pageToPageDTOMapper;

  @PostMapping("/register")
  public @ResponseBody CommonResult<AuthenticationResponse> register (@RequestBody @Valid AuthenticationRegisterRequest authenticationRegisterRequest, BindingResult bindingResult)
          throws Exception {
    if (bindingResult.hasErrors()) {
      return CommonResult.validateFailed();
    }

    AuthenticationResponse authenticationResponse = service.register(authenticationRegisterRequest);

    LOGGER.info("创建用户" + authenticationRegisterRequest.getUsername());
    return CommonResult.success(authenticationResponse);
  }

  @PostMapping("/login")
  public @ResponseBody CommonResult<AuthenticationResponse> login (@RequestBody @Valid AuthenticationRequest authenticationRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return CommonResult.validateFailed();
    }

    AuthenticationResponse authenticationResponse = service.login(authenticationRequest);

    LOGGER.info("登录成功" + authenticationResponse);
    return CommonResult.success(authenticationResponse);
  }

  @GetMapping()
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public @ResponseBody CommonResult<PageDTO<UserDTO>> getAllUsers(@RequestParam(defaultValue = "1") Integer pageNo,
                                                               @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<UserDTO> page = service.getAllUsers(pageNo, pageSize);

    LOGGER.info("获取用户列表数据成功");
    return CommonResult.success(pageToPageDTOMapper.pageToPageDTO(page));
  }
}