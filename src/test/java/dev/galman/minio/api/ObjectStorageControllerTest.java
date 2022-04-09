package dev.galman.minio.api;


import dev.galman.minio.util.ObjectStorageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ObjectStorageControllerTest {

    @Spy
    private ObjectStorageUtil objectStorageUtil;
    private ObjectStorageController objectStorageController;

    @BeforeEach
    void setup(){
        objectStorageController = new ObjectStorageController(objectStorageUtil);
    }


    @Test
    @DisplayName("파일을 보냅니다")
    void postFile(){
        given(objectStorageUtil.uploadFile(any(), anyString())).willReturn("<원하는 주소를 입력해주세요>");
        MockMultipartFile file = new MockMultipartFile("file", "hello2.png", MediaType.IMAGE_PNG_VALUE, "Hello, World!".getBytes());
        String url = objectStorageController.postFile(file);
        assertThat(url).isEqualTo("<원하는 주소를 입력해주세요>");
    }

    @Test
    void testSpyService(){
        System.out.println(objectStorageUtil);
       String result = objectStorageUtil.spyTest();
        assertThat(result).isEqualTo("hello");
    }


}
