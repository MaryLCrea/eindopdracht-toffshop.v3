//package toff.novi.eindopdrachttoffshop.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.ByteArrayInputStream;
//import java.nio.file.Path;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//class FileStorageServiceTest{
//
//    private FileStorageService fileStorageService;
//
//    @Mock
//    private MultipartFile multipartFile;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        fileStorageService = new FileStorageService();
//    }
//
//    @Test
//    void testStoreFileWithMock() throws Exception {
//        when(multipartFile.getOriginalFilename()).thenReturn("testfile.txt");
//        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream("dummy content".getBytes()));
//
//        String storedFileName = fileStorageService.storeFile(multipartFile);
//
//        assertThat(storedFileName).isNotNull().isNotBlank();
//        assertThat(storedFileName).endsWith(".txt");
//
//        Path filePath = Path.of(fileStorageService.getFileStoragePath(), storedFileName);
//        assertThat(filePath).isNotNull();
//    }
//
//    @Test
//    void testStoreFileWithInvalidPath() throws Exception {
//        when(multipartFile.getOriginalFilename()).thenReturn("../evil.txt");
//        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream("bad content".getBytes()));
//
//        assertThrows(RuntimeException.class, () -> fileStorageService.storeFile(multipartFile));
//    }
//
//    @Test
//    void testDeleteFileWithNonexistentFile() {
//        boolean result = fileStorageService.deleteFile("nonexistent.txt");
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    void testGetFileStoragePath() {
//        String path = fileStorageService.getFileStoragePath();
//        assertThat(path).isNotBlank();
//    }
//}
