package org.noses.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class ArnoldShortenatorServiceTest {

    @Test
    public void testGetInterpretedURL() {
        ArnoldShortenatorService service = new ArnoldShortenatorService();
        String inputPath = "/foo";
        String destinationPath = "http://www.google.com/?q={1}";
        String expectedResult = "http://www.google.com/?q={1}";

        String gottenResult = service.getInterpretedURL(inputPath, destinationPath);

        Assert.isTrue(gottenResult.equals(expectedResult), "without proper params, "+expectedResult+" vs "+gottenResult);

        inputPath = "/foo/bar";
        destinationPath = "http://www.google.com/?q={1}";
        expectedResult = "http://www.google.com/?q=bar";
        gottenResult = service.getInterpretedURL(inputPath, destinationPath);

        Assert.isTrue(gottenResult.equals(expectedResult), "with proper params, "+expectedResult+" vs "+gottenResult);
    }
}
