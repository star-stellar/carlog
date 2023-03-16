package com.jafa;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jafa.cofig.RootConfig;
import com.jafa.cofig.ServletConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, ServletConfig.class})
@WebAppConfiguration
public class AppTest {

}
