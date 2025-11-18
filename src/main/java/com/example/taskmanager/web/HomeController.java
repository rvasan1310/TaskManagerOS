package com.example.taskmanager.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Serves the SPA index.html for non-API routes without relying on view resolution.
 */
@Controller
@RequestMapping
public class HomeController {

    private ResponseEntity<Resource> indexResource() {
        Resource resource = new ClassPathResource("static/index.html");
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(resource);
    }

    @GetMapping({"/", "/index", "/items", "/monitor"})
    public ResponseEntity<Resource> root() {
        return indexResource();
    }

    // Single segment (no dots, exclude api/actuator/error)
    @GetMapping("/{path:^(?!api|actuator|error|favicon\\.ico|.*\\..*).*$}")
    public ResponseEntity<Resource> any1(@PathVariable String path) {
        return indexResource();
    }

    // Two segments (no dots)
    @GetMapping("/{p1:^(?!api|actuator|error|.*\\..*).*$}/{p2:^(?!.*\\..*).*$}")
    public ResponseEntity<Resource> any2(@PathVariable String p1, @PathVariable String p2) {
        return indexResource();
    }
}
