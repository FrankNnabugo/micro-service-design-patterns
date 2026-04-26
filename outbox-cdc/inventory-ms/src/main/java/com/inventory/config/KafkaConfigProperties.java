package com.inventory.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KafkaConfigProperties.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaConfigProperties {
    private String bootstrapServers;

}
