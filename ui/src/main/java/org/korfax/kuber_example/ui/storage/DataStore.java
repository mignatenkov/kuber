package org.korfax.kuber_example.ui.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataStore {

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    private Datastore datastore;

    private KeyFactory messageKeyFactory;
    public String MESSAGE_KIND = "Message";

    @PostConstruct
    public void initializeKeyFactories() {
        log.info("Initializing key factories");

        log.debug("spring.cloud.gcp.project-id: " + projectId);
        try {
            datastore = DatastoreOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials
                            .fromStream(new ClassPathResource("google.creds1.json").getInputStream()))
                    .build()
                    .getService();
        } catch (IOException e) {

        }

        messageKeyFactory = datastore.newKeyFactory().setKind(MESSAGE_KIND);
    }

    public void putMessage(String message) {
        Key key = messageKeyFactory.newKey(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        Entity entity = Entity.newBuilder(key).set("message", message).build();
        datastore.put(entity);
    }

    public void deleteMessage(Long id) {
        Key key = messageKeyFactory.newKey(id);
        datastore.delete(key);
    }

    public String getMessage(Long id) {
        Key key = messageKeyFactory.newKey(id);
        return datastore.get(key).getString("message");
    }

    public List<String> getAllMessages() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(MESSAGE_KIND)
                .build();
        QueryResults<Entity> results = datastore.run(query);
        List<String> retVals = new ArrayList<>();
        while(results.hasNext()) {
            Entity entity = results.next();
            retVals.add(entity.getString("message"));
        }
        return retVals;
    }
}