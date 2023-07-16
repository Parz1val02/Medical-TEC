package com.example.medicaltec.controller;

public interface ErrorController extends org.springframework.boot.web.servlet.error.ErrorController {
    String getErrorPath();
}