package com.example.springbootccpms.service;

import com.example.springbootccpms.mapper.ExportCsvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private ExportCsvMapper exportCsvMapper;
}
