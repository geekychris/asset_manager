package com.example.artifactmanager.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    /**
     * Initialize the storage
     */
    void init();
    
    /**
     * Store a file
     * @param file The file to store
     * @return The path to the stored file
     */
    String store(MultipartFile file) throws IOException;
    
    /**
     * Get the path for a filename
     * @param filename The filename
     * @return The path to the file
     */
    Path load(String filename);
    
    /**
     * Delete a file
     * @param filename The filename to delete
     */
    void delete(String filename) throws IOException;
    
    /**
     * Generate a unique filename for the uploaded file
     * @param originalFilename The original filename
     * @return A unique filename
     */
    String generateUniqueFilename(String originalFilename);
}

