package api.tsurusaki.domain.service;

import api.tsurusaki.domain.value.Sample;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public Sample sample() {
        return Sample.builder()
                .message("Sample message.")
                .build();
    }
}
