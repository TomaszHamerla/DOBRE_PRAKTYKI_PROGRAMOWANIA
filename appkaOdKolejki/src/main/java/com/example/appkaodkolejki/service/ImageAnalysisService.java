package com.example.appkaodkolejki.service;

import jakarta.annotation.PostConstruct;
import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageAnalysisService {

    private Net net;
    private List<String> outBlobNames;

    @PostConstruct
    public void initModel() {
        String modelWeights = "yolov3.weights";
        String modelConfig = "yolov3.cfg";

        this.net = Dnn.readNetFromDarknet(modelConfig, modelWeights);

        this.net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        this.net.setPreferableTarget(Dnn.DNN_TARGET_CPU);

        this.outBlobNames = getOutputNames(net);

        if (this.net.empty()) {
            System.err.println("Błąd! Nie udało się załadować YOLO.");
        } else {
            System.out.println("Model YOLOv3 załadowany pomyślnie.");
        }
    }

    public int countPeople(String imageUrl) throws Exception {
        byte[] imageBytes = downloadImage(imageUrl);
        Mat image = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);

        if (image.empty()) {
            throw new IllegalArgumentException("Nie udało się zdekodować obrazu.");
        }

        Mat blob = Dnn.blobFromImage(image, 1.0 / 255.0, new Size(416, 416), new Scalar(0), true, false);

        net.setInput(blob);

        List<Mat> result = new ArrayList<>();
        net.forward(result, outBlobNames);

        List<Integer> classIds = new ArrayList<>();
        List<Float> confidences = new ArrayList<>();
        List<Rect2d> boxes = new ArrayList<>();

        for (Mat level : result) {
            for (int i = 0; i < level.rows(); i++) {
                Mat row = level.row(i);
                Mat scores = row.colRange(5, level.cols());

                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                float confidence = (float) mm.maxVal;

                if (confidence > 0.5f) {
                    Point classIdPoint = mm.maxLoc;
                    int classId = (int) classIdPoint.x;

                    if (classId == 0) {
                        int centerX = (int) (row.get(0, 0)[0] * image.cols());
                        int centerY = (int) (row.get(0, 1)[0] * image.rows());
                        int width = (int) (row.get(0, 2)[0] * image.cols());
                        int height = (int) (row.get(0, 3)[0] * image.rows());
                        int left = centerX - width / 2;
                        int top = centerY - height / 2;

                        classIds.add(classId);
                        confidences.add(confidence);
                        boxes.add(new Rect2d(left, top, width, height));
                    }
                }
            }
        }

        MatOfRect2d boxesMat = new MatOfRect2d();
        boxesMat.fromList(boxes);

        MatOfFloat confidencesMat = new MatOfFloat();
        confidencesMat.fromList(confidences);

        MatOfInt indices = new MatOfInt();
        Dnn.NMSBoxes(boxesMat, confidencesMat, 0.5f, 0.4f, indices);

        int peopleCount = (int) indices.total();

        image.release();
        blob.release();
        for (Mat m : result) m.release();

        return peopleCount;
    }

    private static List<String> getOutputNames(Net net) {
        List<String> names = new ArrayList<>();
        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();

        for (int i : outLayers) {
            names.add(layersNames.get(i - 1));
        }
        return names;
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