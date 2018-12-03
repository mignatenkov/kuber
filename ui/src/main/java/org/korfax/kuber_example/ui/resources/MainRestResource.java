package org.korfax.kuber_example.ui.resources;

import org.korfax.kuber_example.ui.storage.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainRestResource {

    @Autowired
    private DataStore dataStore;

    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    public ResponseEntity getAll() {
        List<String> body = dataStore.getAllMessages();

        return ResponseEntity.ok(body);
    }

}