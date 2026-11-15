package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${UPLOAD_PATH}")
    private String uploadPath;

    private static final List<String> EXTENSIONES_PERMITIDAS = List.of("jpg", "jpeg", "png", "webp");

    /**
     * Guarda un archivo en el sistema de archivos y devuelve el nombre guardado.
     * @return El nombre del archivo guardado o null si ocurre un error.
     */
    public String saveFile(MultipartFile file) {
        try {
            String fileExtension = getFileExtension(file.getOriginalFilename());

            // Validar que la extensión es una imagen permitida
            if (!EXTENSIONES_PERMITIDAS.contains(fileExtension.toLowerCase())) {
                logger.error("Tipo de archivo no permitido: {}", fileExtension);
                return null;
            }

            String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;
            Path filePath = Paths.get(uploadPath + File.separator + uniqueFileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            logger.info("Archivo {} guardado con éxito.", uniqueFileName);
            return uniqueFileName;

        } catch (IOException e) {
            logger.error("Error al guardar el archivo: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Elimina un archivo del sistema de archivos.
     */
    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadPath, fileName);
            Files.deleteIfExists(filePath);
            logger.info("Archivo {} eliminado con éxito.", fileName);
        } catch (IOException e) {
            logger.error("Error al eliminar el archivo {}: {}", fileName, e.getMessage());
        }
    }

    /**
     * Obtiene la extensión del archivo o cadena vacía si no tiene.
     */
    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}