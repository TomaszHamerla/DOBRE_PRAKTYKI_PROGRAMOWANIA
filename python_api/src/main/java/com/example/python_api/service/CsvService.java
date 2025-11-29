package com.example.python_api.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class CsvService {

    public <T> List<T> loadCsv(String fileName, Function<String[], T> mapper, int skipLines) {
        List<T> list = new ArrayList<>();
        try {
            var resource = new ClassPathResource("data/" + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            for (int i = 0; i < skipLines; i++) {
                br.readLine();
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                list.add(mapper.apply(parts));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
