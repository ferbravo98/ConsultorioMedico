package consultorio;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupManager {

    public static String crearBackup() throws IOException {

        Path dbOrigen = Paths.get("consultorio.db");

        if (!Files.exists(dbOrigen)) {
            throw new IOException("No se encontró la base de datos.");
        }

        Path carpetaBackups = Paths.get("backups");

        if (!Files.exists(carpetaBackups)) {
            Files.createDirectories(carpetaBackups);
        }

        String fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"));

        Path backupDestino = carpetaBackups.resolve(
                "backup_" + fecha + ".db"
        );

        Files.copy(
                dbOrigen,
                backupDestino,
                StandardCopyOption.REPLACE_EXISTING
        );

        return backupDestino.toString();
    }
}