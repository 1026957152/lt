package com.lt.dom.controllerOct;

import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.api.TaskApi;
import org.camunda.community.rest.client.dto.ProcessInstanceWithVariablesDto;
import org.camunda.community.rest.client.dto.StartProcessInstanceDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ExampleRestEndpoint {

    @Autowired
    private ProcessDefinitionApi processDefinitionApi;

    @Autowired
    private TaskApi taskApi;


    @PutMapping("/start")
    public ResponseEntity<String> startProcess() throws ApiException {
        // ...

        Map variables = new HashMap<>();
        // start process instance
        ProcessInstanceWithVariablesDto processInstance = processDefinitionApi.startProcessInstanceByKey(
                "lt-process",
                new StartProcessInstanceDto().variables(variables));
        // ...

        return ResponseEntity.ok(processInstance.toString());

    }










    @PutMapping("/getTask")
    public ResponseEntity<String> getTask() throws ApiException {
        // ...




        Map variables = new HashMap<>();
        // start process instance
        ProcessInstanceWithVariablesDto processInstance = processDefinitionApi.startProcessInstanceByKey(
                "lt-process",
                new StartProcessInstanceDto().variables(variables));
        // ...

        return ResponseEntity.ok(processInstance.toString());

    }

}