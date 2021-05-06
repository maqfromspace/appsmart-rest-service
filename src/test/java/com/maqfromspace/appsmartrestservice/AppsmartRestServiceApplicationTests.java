package com.maqfromspace.appsmartrestservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppsmartRestServiceApplicationTests {


        @Test
        void AppStart() {
            AppsmartRestServiceApplication.main(new String[]{});
            assertThat(Boolean.TRUE).isTrue();
        }

}
