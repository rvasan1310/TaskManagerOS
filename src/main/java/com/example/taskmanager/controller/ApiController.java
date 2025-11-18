package com.example.taskmanager.controller;

import com.example.taskmanager.model.ProcessInfo;
import com.example.taskmanager.model.SystemStats;
import com.example.taskmanager.service.SimulatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    private final SimulatorService simulator;

    public ApiController(SimulatorService simulator) {
        this.simulator = simulator;
    }

    @GetMapping("/api/processes")
    public List<ProcessInfo> processes() {
        return simulator.getProcesses();
    }

    @GetMapping("/api/stats")
    public SystemStats stats() {
        return simulator.getSystemStats();
    }

    @PostMapping("/api/kill/{pid}")
    public ResponseEntity<?> kill(@PathVariable int pid) {
        boolean ok = simulator.killProcess(pid);
        if (ok) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Cannot terminate this process (may be system/kernel or not found).");
    }
}
