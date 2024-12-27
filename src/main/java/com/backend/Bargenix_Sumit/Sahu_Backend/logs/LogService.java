package com.backend.Bargenix_Sumit.Sahu_Backend.logs;

import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Log;

import java.util.List;

public interface LogService {
    Log saveLog(Log log);
    List<Log> getAllLogs();
    Log getLogById(Long id);
    void deleteLog(Long id);
}
