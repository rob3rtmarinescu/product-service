package ing.product_service.utils;

import ing.product_service.exception.BadRequestException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {

    private FileUtils() {
    }

    public static String readFileAsString(final String path) {
        try {
            final ClassLoader classLoader = FileUtils.class.getClassLoader();
            final File jsonFile = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
            return new String((Files.readAllBytes((Paths.get(jsonFile.getAbsolutePath())))));
        } catch (final Exception e) {
            throw new BadRequestException("Error reading file at " + path);
        }
    }
}
