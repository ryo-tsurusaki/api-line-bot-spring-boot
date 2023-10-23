package api.tsurusaki.presentation.controller;

import api.tsurusaki.domain.service.SampleService;
import api.tsurusaki.domain.value.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @GetMapping("/sample")
    public Sample sample() {

        return this.sampleService.sample();
    }
}
