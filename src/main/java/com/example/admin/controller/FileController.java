package com.example.admin.controller;

import com.example.admin.common.api.CommonResult;
import com.example.admin.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public CommonResult<String> uploadFile(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        String url = fileService.upload(file);
        return CommonResult.success(url);
    }

}
