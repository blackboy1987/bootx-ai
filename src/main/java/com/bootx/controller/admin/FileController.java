package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/file")
public class FileController extends BaseController {

    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        return Result.success(file.getName());
    }

}
