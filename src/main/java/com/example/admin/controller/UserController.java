package com.example.admin.controller;

import com.example.admin.common.api.CommonResult;
import com.example.admin.mapper.PageToPageDTOMapper;
import com.example.admin.model.PageDTO;
import com.example.admin.model.User;
import com.example.admin.model.UserDTO;
import com.example.admin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

  @PostMapping()
  public @ResponseBody CommonResult<UserDTO> createUser (@RequestBody @Valid User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return CommonResult.validateFailed();
    }

    User nUser = service.createUser(user);
    String userName = nUser.getUserName();
    String email = nUser.getEmail();

    UserDTO userDTO = new UserDTO(userName, email);
    LOGGER.info("创建用户" + userName);
    return CommonResult.success(userDTO);
  }

  @GetMapping()
  public @ResponseBody CommonResult<PageDTO<UserDTO>> getAllUsers(@RequestParam(defaultValue = "1") Integer pageNo,
                                                               @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<UserDTO> page = service.getAllUsers(pageNo, pageSize);

    LOGGER.info("获取用户列表数据成功");
    return CommonResult.success(pageToPageDTOMapper.pageToPageDTO(page));
  }
}