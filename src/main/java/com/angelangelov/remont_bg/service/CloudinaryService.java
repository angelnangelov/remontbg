package com.angelangelov.remont_bg.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadImg(MultipartFile multipartFile) throws IOException;

}
