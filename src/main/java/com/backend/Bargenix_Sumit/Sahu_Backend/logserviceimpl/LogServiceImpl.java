package com.backend.Bargenix_Sumit.Sahu_Backend.logserviceimpl;

import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Log;
import com.backend.Bargenix_Sumit.Sahu_Backend.repository.LogRepository;
import com.backend.Bargenix_Sumit.Sahu_Backend.logs.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;


    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Log saveLog(Log log) {
        return logRepository.save(log);
    }

    @Override
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    @Override
    public Log getLogById(Long id) {
        Optional<Log> log = logRepository.findById(id);
        return log.orElseThrow(() -> new RuntimeException("Log not found with id: " + id));
    }

    @Override
    public void deleteLog(Long id) {
        logRepository.deleteById(id);

    }
}
