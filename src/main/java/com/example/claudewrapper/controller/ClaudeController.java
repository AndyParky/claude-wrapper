package com.example.claudewrapper.controller;

import com.example.claudewrapper.service.ClaudeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClaudeController {

    @Autowired
    private ClaudeService claudeService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/ask")
    public String askClaude(@RequestParam("question") String question, Model model) {
        String answer = claudeService.askClaude(question);
        model.addAttribute("question", question);
        model.addAttribute("answer", answer);
        return "index";
    }
}
