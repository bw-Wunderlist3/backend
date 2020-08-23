package com.wunderlist.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
    @Transactional
    @Override
    public void run(String[] args) throws Exception {
    }
}
