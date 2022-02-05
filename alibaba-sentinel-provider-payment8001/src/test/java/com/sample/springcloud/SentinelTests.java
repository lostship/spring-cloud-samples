package com.sample.springcloud;

import org.junit.jupiter.api.Test;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SentinelTests {

    @Test
    public void ruleTest() throws JsonProcessingException {
        FlowRule flowRule1 = new FlowRule();
        flowRule1.setResource("test");
        flowRule1.setLimitApp("default");
        flowRule1.setGrade(1);
        flowRule1.setCount(1);
        flowRule1.setStrategy(0);
        flowRule1.setControlBehavior(0);
        flowRule1.setClusterMode(false);

        DegradeRule degradeRule1 = new DegradeRule();
        degradeRule1.setResource("test");
        degradeRule1.setLimitApp("default");
        degradeRule1.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        degradeRule1.setCount(200);
        degradeRule1.setSlowRatioThreshold(0.5);
        degradeRule1.setTimeWindow(1000);
        degradeRule1.setMinRequestAmount(5);
        degradeRule1.setStatIntervalMs(1);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(Include.NON_NULL);
        System.out.println(mapper.writeValueAsString(flowRule1));
        System.out.println(mapper.writeValueAsString(degradeRule1));
    }

}
