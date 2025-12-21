package com.example.appkaodkolejki.service;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ImageAnalysisService {

    public int countPeople(String imageUrl) throws Exception {
        byte[] imageBytes = downloadImage(imageUrl);
        Mat image = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);

        if (image.empty()) {
            throw new IllegalArgumentException("Nie udało się zdekodować obrazu z podanego URL.");
        }

        HOGDescriptor hog = null;
        MatOfRect foundLocations = new MatOfRect();
        MatOfDouble foundWeights = new MatOfDouble();

        try {
            hog = new HOGDescriptor();
            hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());

            hog.detectMultiScale(
                    image,
                    foundLocations,
                    foundWeights,
                    0.0,
                    new Size(4, 4),
                    new Size(32, 32),
                    1.05,
                    0.5,
                    false
            );

            return foundLocations.toArray().length;

        } finally {
            if (image != null) image.release();
            if (foundLocations != null) foundLocations.release();
            if (foundWeights != null) foundWeights.release();
        }
    }

    private byte[] downloadImage(String urlString) throws Exception {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

        try (InputStream in = connection.getInputStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int n;
            while (-1 != (n = in.read(buffer))) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        }
    }
}
