package com.example.be

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureMockMvc
open class SpringMockMvcTestSupport : SpringTestSupport() {

    @Autowired
    lateinit var mockMvc: MockMvc
}
