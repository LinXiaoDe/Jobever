package com.lin.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    void getFileAbsolutePath() {
        System.out.println(FileUtil.getFileAbsolutePath("/home/qdl/Desktop/gallery/"+".cache/","jpg"));
    }
}